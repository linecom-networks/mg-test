package com.mango.model.dto;

import com.mango.model.util.Constants;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class SloganDTO {

	@NotNull(message = Constants.NOT_NULL_FIELD)
	private Long userId;

	@Size(max = 200)
	@NotNull(message = Constants.NOT_NULL_FIELD)
	@NotEmpty(message = Constants.NOT_EMPTY_FIELD)
	private String description;

}
