package com.mango.businesslogic;

import com.mango.model.dto.SloganDTO;
import com.mango.model.dto.UserDTO;

public interface IUserService {

	/**
	 * Method that creates new user in DB
	 * @param userDTO user to be added
	 * @return Identifier user in DB
	 */
	Long createUser(UserDTO userDTO) throws Exception;

	/**
	 * Method that updates current user in DB
	 * @param userDTO user to be updated
	 * @return Identifier user in DB
	 */
	Long updateUser(UserDTO userDTO) throws Exception;

	/**
	 * Method that creates new slogan in DB
	 * @param sloganDTO slogan attributs such as associated userId and description
	 * @throws Exception if max slogans reached
	 * @return Actual num of slogans
	 */
	Integer addSlogan(SloganDTO sloganDTO) throws Exception;

	/**
	 * Returns the user saved in BD
	 * @param userId user identifier
	 * @return All user info
	 */
	UserDTO getUser(Long userId);
}
