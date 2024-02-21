package com.mango.model.dto;

import com.mango.model.util.Constants;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserDTO {

	private Long userId;

	@Size(max = 20)
	@NotNull(message = Constants.NOT_NULL_FIELD)
	@NotEmpty(message = Constants.NOT_EMPTY_FIELD)
	private String name;

	@Size(max = 30)
	@NotNull(message = Constants.NOT_NULL_FIELD)
	@NotEmpty(message = Constants.NOT_EMPTY_FIELD)
	private String lastName;

	@Size(max = 50)
	@NotNull(message = Constants.NOT_NULL_FIELD)
	@NotEmpty(message = Constants.NOT_EMPTY_FIELD)
	private String address;

	@Size(max = 20)
	@NotNull(message = Constants.NOT_NULL_FIELD)
	@NotEmpty(message = Constants.NOT_EMPTY_FIELD)
	private String city;

	@Size(max = 20)
	@NotNull(message = Constants.NOT_NULL_FIELD)
	@NotEmpty(message = Constants.NOT_EMPTY_FIELD)
	private String email;
}
