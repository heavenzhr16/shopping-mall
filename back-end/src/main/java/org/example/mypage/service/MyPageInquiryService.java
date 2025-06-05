package org.example.mypage.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.InquiryEntity;
import org.example.entity.InquiryReplyEntity;
import org.example.entity.ProductsEntity;
import org.example.entity.UsersEntity;
import org.example.exception.CustomException;
import org.example.exception.ErrorCode;
import org.example.mypage.dto.InquiryReplyDto;
import org.example.mypage.dto.InquiryRequestDto;
import org.example.mypage.dto.InquiryResponseDto;
import org.example.mypage.repository.InquiryReplyRepository;
import org.example.mypage.repository.InquiryRepository;
import org.example.mypage.repository.ProductRepository;
import org.example.mypage.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 마이페이지 - 문의 관련 서비스
 */
@Service
@RequiredArgsConstructor
public class MyPageInquiryService {

  private final InquiryRepository inquiryRepository;
  private final InquiryReplyRepository inquiryReplyRepository;
  private final UserRepository userRepository;
  private final ProductRepository productRepository;

  /**
   * 문의 등록
   */
  @Transactional
  public void createInquiry(InquiryRequestDto dto) {
    UsersEntity user = userRepository.findById(dto.getUserId())
        .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    ProductsEntity product = productRepository.findById(dto.getProductId())
        .orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

    InquiryEntity inquiry = new InquiryEntity();
    inquiry.setUser(user);
    inquiry.setProduct(product);
    inquiry.setInquiryType(dto.getInquiryType());
    inquiry.setInquiryTitle(dto.getInquiryTitle());
    inquiry.setInquiryContent(dto.getInquiryContent());
    inquiry.setIsSecret(dto.getIsSecret());
    inquiry.setIsResponded(false);
    inquiry.setCreatedAt(LocalDateTime.now());
    inquiry.setName(dto.getName());
    inquiry.setEmail(dto.getEmail());
    inquiry.setPassword(dto.getPassword());

    inquiryRepository.save(inquiry);
  }

  /**
   * 내 문의 목록 조회
   */
  @Transactional(readOnly = true)
  public List<InquiryResponseDto> getMyInquiries(Long userId) {
    return inquiryRepository.findByUser_UserIdOrderByCreatedAtDesc(userId)
        .stream()
        .map(inquiry -> {
          InquiryResponseDto dto = new InquiryResponseDto();
          dto.setInquiryId(inquiry.getInquiryId());
          dto.setInquiryType(inquiry.getInquiryType());
          dto.setInquiryTitle(inquiry.getInquiryTitle());
          dto.setInquiryContent(inquiry.getInquiryContent());
          dto.setIsSecret(inquiry.getIsSecret());
          dto.setIsResponded(inquiry.getIsResponded());
          dto.setCreatedAt(inquiry.getCreatedAt());

          List<InquiryReplyDto> replies = inquiryReplyRepository
              .findByInquiry_InquiryIdOrderByCreatedAtAsc(inquiry.getInquiryId())
              .stream()
              .map(reply -> new InquiryReplyDto(
                  reply.getReplyTitle(),
                  reply.getReplyContent(),
                  reply.getCreatedAt()
              ))
              .collect(Collectors.toList());

          dto.setReplies(replies);
          return dto;
        }).collect(Collectors.toList());
  }

  /**
   * 문의 상세 조회
   * - 본인의 문의만 조회 가능
   */
  @Transactional(readOnly = true)
  public InquiryResponseDto getInquiryDetail(Long userId, Long inquiryId) {
    InquiryEntity inquiry = inquiryRepository.findById(inquiryId)
        .orElseThrow(() -> new CustomException(ErrorCode.INQUIRY_NOT_FOUND));

    if (!inquiry.getUser().getUserId().equals(userId)) {
      throw new CustomException(ErrorCode.INQUIRY_ACCESS_DENIED);
    }

    InquiryResponseDto dto = new InquiryResponseDto();
    dto.setInquiryId(inquiry.getInquiryId());
    dto.setInquiryType(inquiry.getInquiryType());
    dto.setInquiryTitle(inquiry.getInquiryTitle());
    dto.setInquiryContent(inquiry.getInquiryContent());
    dto.setCreatedAt(inquiry.getCreatedAt());
    dto.setIsSecret(inquiry.getIsSecret());
    dto.setIsResponded(inquiry.getIsResponded());

    dto.setReplies(
        inquiry.getInquiryReplies().stream()
            .map(reply -> new InquiryReplyDto(
                reply.getReplyTitle(),
                reply.getReplyContent(),
                reply.getCreatedAt()
            )).collect(Collectors.toList())
    );

    return dto;
  }
}
