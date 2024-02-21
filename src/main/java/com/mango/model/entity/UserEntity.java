package com.mango.model.entity;

import com.mango.model.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import javax.persistence.*;

@Entity
@Table(name="USERS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", nullable = false)
	private Long id;

	@Size(max = 20)
	@NotNull(message = Constants.NOT_NULL_FIELD)
	@NotEmpty(message = Constants.NOT_EMPTY_FIELD)
	@Column(name = "NAME")
	private String name;

	@Size(max = 30)
	@NotNull(message = Constants.NOT_NULL_FIELD)
	@NotEmpty(message = Constants.NOT_EMPTY_FIELD)
	@Column(name = "LAST_NAME")
	private String lastName;

	@Size(max = 50)
	@NotNull(message = Constants.NOT_NULL_FIELD)
	@NotEmpty(message = Constants.NOT_EMPTY_FIELD)
	@Column(name = "ADDRESS")
	private String address;

	@Size(max = 20)
	@NotNull(message = Constants.NOT_NULL_FIELD)
	@NotEmpty(message = Constants.NOT_EMPTY_FIELD)
	@Column(name = "CITY")
	private String city;

	@Size(max = 20)
	@NotNull(message = Constants.NOT_NULL_FIELD)
	@NotEmpty(message = Constants.NOT_EMPTY_FIELD)
	@Column(name = "EMAIL")
	private String email;



}
