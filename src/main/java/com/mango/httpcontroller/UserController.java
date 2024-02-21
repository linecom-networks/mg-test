package com.mango.httpcontroller;

import com.mango.businesslogic.IUserService;
import com.mango.model.dto.ResponseSloganDTO;
import com.mango.model.dto.ResponseUserDTO;
import com.mango.model.dto.SloganDTO;
import com.mango.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	@GetMapping(path = "{userId}")
	public UserDTO getUser(@NotNull @PathVariable Long userId){
		try {
			return userService.getUser(userId);
		}catch (Exception e){
			return null;
		}
	}

	@PostMapping
	public ResponseUserDTO createUser(@Valid @RequestBody UserDTO userDTO){
		ResponseUserDTO response = new ResponseUserDTO();
		try{
			response.setUserId(userService.createUser(userDTO));
			response.setResult("User created with id: " + response.getUserId());
		}catch (Exception e){
			response.setResult("Error creating new user: " + e.getMessage());
		}

		return response;
	}

	@PutMapping
	public ResponseUserDTO updateUser(@RequestBody @Valid UserDTO userDTO){
		ResponseUserDTO response = new ResponseUserDTO();
		try{
			response.setUserId(userService.updateUser(userDTO));
			response.setResult("User updated with id: " + response.getUserId());
		}catch (Exception e){
			response.setResult("Error updating user: " + e.getMessage());
		}
		return response;

	}

	@PostMapping("/slogan")
	public ResponseSloganDTO addSlogan(@RequestBody @Valid SloganDTO sloganDTO){
		ResponseSloganDTO response = new ResponseSloganDTO();
		try {
			response.setNumSlogansActuales(userService.addSlogan(sloganDTO));
			response.setResult("Slogan saved.");
		} catch (Exception e) {
			response.setResult("Error creating slogan: " + e.getMessage());
		}
		return response;
	}
}
