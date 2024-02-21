package com.mango.businesslogic.impl;

import com.mango.businesslogic.IUserService;
import com.mango.data.repository.SloganRepository;
import com.mango.data.repository.UserRepository;
import com.mango.model.dto.SloganDTO;
import com.mango.model.dto.UserDTO;
import com.mango.model.entity.SloganEntity;
import com.mango.model.entity.UserEntity;
import com.mango.model.util.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SloganRepository sloganRepository;

	@Autowired
	private UserUtils userUtils;

	@Value("${max.slogans:3}")
	private  Long MAX_SLOGANS;

	@Override
	public Long createUser(UserDTO userDTO) throws Exception {

		try{
			//Build Entity
			UserEntity entity = userUtils.mapUserEntity(userDTO);
			entity = userRepository.save(entity);

			return entity.getId();

		}catch (Exception e){
			logger.error(e.getMessage());
			throw new Exception("Error saving user");
		}

	}

	@Override
	public Long updateUser(UserDTO userDTO) throws Exception {

		if(!checkUserExists(userDTO.getUserId())){
			throw new Exception("User with ID: " + userDTO.getUserId() + " not exists");
		}

		try{
			UserEntity entity = userUtils.mapUserEntity(userDTO);
			userRepository.save(entity);

			return entity.getId();
		}catch (Exception e){
			logger.error(e.getMessage());
			throw new Exception("Error updating user with id: " + userDTO.getUserId());
		}
	}

	private boolean checkUserExists(Long userId){
		try {
			UserEntity actual = userRepository.findById(userId).get();
			return true;
		}catch (NoSuchElementException e){
			return false;
		}
	}

	@Override
	public Integer addSlogan(SloganDTO sloganDTO) throws Exception {

		//Check if user exists
		if(!checkUserExists(sloganDTO.getUserId())){
			throw new Exception("User with ID: " + sloganDTO.getUserId() + " not exists");
		}


		List<SloganEntity> userSlogans = sloganRepository.findByUserId(sloganDTO.getUserId());

		//Check
		if(userSlogans.size()==MAX_SLOGANS){
			throw new Exception("Max number of slogans " + MAX_SLOGANS + " reached for the user");
		}else{
			try{
				SloganEntity sloganEntity = userUtils.mapSloganEntity(sloganDTO);
				sloganRepository.save(sloganEntity);
				return userSlogans.size() + 1;

			}catch (Exception e){
				logger.error(e.getMessage());
				throw new Exception("Error adding new slogan, user with id: " + sloganDTO.getUserId());
			}
		}

	}

	@Override
	public UserDTO getUser(Long userId){
		return userUtils.mapUserDTO(userRepository.findById(userId).get());
	}

}
