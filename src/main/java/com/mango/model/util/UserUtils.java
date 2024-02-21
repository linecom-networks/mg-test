package com.mango.model.util;

import com.mango.model.dto.SloganDTO;
import com.mango.model.dto.UserDTO;
import com.mango.model.entity.SloganEntity;
import com.mango.model.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserUtils {

	public UserEntity mapUserEntity(UserDTO userDTO){
		return UserEntity.builder()
			.id(userDTO.getUserId())
			.name(userDTO.getName())
			.lastName(userDTO.getLastName())
			.address(userDTO.getAddress())
			.city(userDTO.getCity())
			.email(userDTO.getEmail())
			.build();
	}

	public SloganEntity mapSloganEntity(SloganDTO dto){
		return SloganEntity.builder().userId(dto.getUserId())
			.description(dto.getDescription())
			.build();
	}

	public UserDTO mapUserDTO(UserEntity entity){
		UserDTO dto = new UserDTO();
		dto.setUserId(entity.getId());
		dto.setEmail(entity.getEmail());
		dto.setAddress(entity.getAddress());
		dto.setName(entity.getName());
		dto.setLastName(entity.getLastName());
		dto.setCity(entity.getCity());
		return dto;
	}
}
