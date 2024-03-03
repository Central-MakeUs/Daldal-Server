package com.mm.api.domain.point.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.mm.api.domain.auth.service.AuthService;
import com.mm.coredomain.domain.Member;
import com.mm.coredomain.repository.MemberRepository;
import com.mm.coresecurity.oauth.OAuth2UserDetails;

@SpringBootTest
@ActiveProfiles("stage")
class PointServiceTest {
	@Autowired
	private AuthService authService;
	@Autowired
	private PointService pointService;
	@Autowired
	private MemberRepository memberRepository;

	@Test
	void 포인트_출금요청_동시성처리_성공() throws InterruptedException {
		// given
		Member member = Member.builder()
			.email("test")
			.point(10000)
			.build();

		Member savedMember = memberRepository.saveAndFlush(member);
		OAuth2UserDetails userDetails = OAuth2UserDetails.builder()
			.id(savedMember.getId())
			.build();

		ExecutorService executorService = Executors.newFixedThreadPool(5);
		CountDownLatch countDownLatch = new CountDownLatch(5);

		// when
		for (int i = 0; i < 5; i++) {
			executorService.submit(() -> {
				try {
					pointService.postPointsWithdraw(userDetails, 1000);
				} finally {
					countDownLatch.countDown();
				}
			});
		}
		countDownLatch.await();

		// then
		Integer point = memberRepository.findById(savedMember.getId()).orElseThrow().getPoint();
		assertEquals(5000, point);
	}
}
