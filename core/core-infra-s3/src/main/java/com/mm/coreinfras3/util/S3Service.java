package com.mm.coreinfras3.util;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3Service {
	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public String uploadFileToS3(MultipartFile file, Long memberId, Long itemId) {
		try {
			ObjectMetadata metadata = new ObjectMetadata();
			metadata.setContentType(file.getContentType());
			metadata.setContentLength(file.getSize());
			String filename = getUploadFileName(memberId, itemId);
			amazonS3Client.putObject(bucket, filename, file.getInputStream(), metadata);
			return String.valueOf(amazonS3Client.getUrl(bucket, filename));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void delete(String fileName) {
		DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(bucket, fileName);
		amazonS3Client.deleteObject(deleteObjectRequest);
	}

	private String getUploadFileName(Long memberId, Long itemId) {
		StringBuffer stringBuffer = new StringBuffer();
		String currentTime = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS").format(System.currentTimeMillis());
		stringBuffer.append(currentTime).append("-").append(memberId).append("-").append(itemId);
		return stringBuffer.toString();
	}
}
