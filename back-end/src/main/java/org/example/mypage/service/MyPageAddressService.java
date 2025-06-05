package org.example.mypage.service;

import lombok.RequiredArgsConstructor;
import org.example.entity.UserInfoEntity;
import org.example.exception.CustomException;
import org.example.exception.ErrorCode;
import org.example.mypage.dto.AddressRequestDto;
import org.example.mypage.repository.UserInfoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 마이페이지 - 배송지 관련 서비스
 */
@Service
@RequiredArgsConstructor
public class MyPageAddressService {

  private final UserInfoRepository userInfoRepository;

  /**
   * 배송지 추가
   * - 기본 배송지 설정 시 기존 기본 배송지를 해제함
   */
  @Transactional
  public void addAddress(Long userId, AddressRequestDto dto) {
    if (dto.getIsDefault() != null && dto.getIsDefault()) {
      userInfoRepository.findByUserIdAndIsDefaultTrue(userId)
          .ifPresent(addr -> {
            addr.setIsDefault(false);
            addr.setUpdatedAt(LocalDateTime.now());
            userInfoRepository.save(addr);
          });
    }

    UserInfoEntity address = new UserInfoEntity();
    address.setAddressId(dto.getAddressId()); // 클라이언트가 넘겨줘야 함 (또는 UUID 생성 가능)
    address.setUserId(userId);
    address.setRecipientName(dto.getRecipientName());
    address.setRecipientPhone(dto.getRecipientPhone());
    address.setPostcode(dto.getPostcode());
    address.setAddressLine1(dto.getAddressLine1());
    address.setAddressLine2(dto.getAddressLine2());
    address.setAddressAlias(dto.getAddressAlias());
    address.setDeliveryMessage(dto.getDeliveryMessage());
    address.setIsDefault(dto.getIsDefault() != null ? dto.getIsDefault() : false);
    address.setCreatedAt(LocalDateTime.now());

    userInfoRepository.save(address);
  }

  /**
   * 배송지 수정
   * - 기본 배송지 설정 시 기존 기본 배송지를 해제함
   */
  @Transactional
  public void updateAddress(Long userId, Long addressId, AddressRequestDto dto) {
    UserInfoEntity address = userInfoRepository
        .findById(new UserInfoEntity.UserInfoId(addressId, userId))
        .orElseThrow(() -> new CustomException(ErrorCode.ADDRESS_NOT_FOUND));

    if (dto.getIsDefault() != null && dto.getIsDefault()) {
      userInfoRepository.findByUserIdAndIsDefaultTrue(userId)
          .filter(existing -> !existing.getAddressId().equals(addressId))
          .ifPresent(existing -> {
            existing.setIsDefault(false);
            existing.setUpdatedAt(LocalDateTime.now());
            userInfoRepository.save(existing);
          });
      address.setIsDefault(true);
    }

    address.setRecipientName(dto.getRecipientName());
    address.setRecipientPhone(dto.getRecipientPhone());
    address.setPostcode(dto.getPostcode());
    address.setAddressLine1(dto.getAddressLine1());
    address.setAddressLine2(dto.getAddressLine2());
    address.setAddressAlias(dto.getAddressAlias());
    address.setDeliveryMessage(dto.getDeliveryMessage());
    address.setUpdatedAt(LocalDateTime.now());

    userInfoRepository.save(address);
  }

  /**
   * 배송지 삭제
   * - 최소 한 개의 배송지를 유지해야 하므로 하나 남은 경우 삭제 불가
   */
  @Transactional
  public void deleteAddress(Long userId, Long addressId) {
    List<UserInfoEntity> addresses = userInfoRepository.findAllByUserId(userId);

    if (addresses == null || addresses.size() <= 1) {
      throw new CustomException(ErrorCode.ADDRESS_DELETE_REJECTED);
    }

    UserInfoEntity.UserInfoId id = new UserInfoEntity.UserInfoId(addressId, userId);

    UserInfoEntity target = userInfoRepository.findById(id)
        .orElseThrow(() -> new CustomException(ErrorCode.ADDRESS_NOT_FOUND));

    userInfoRepository.delete(target);
  }
}
