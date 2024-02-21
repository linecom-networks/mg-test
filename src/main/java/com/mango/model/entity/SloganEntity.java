package com.mango.model.entity;

import com.mango.model.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="SLOGANS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SloganEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "ID", nullable = false)
	private Long id;

	@NotNull(message = Constants.NOT_NULL_FIELD)
	@Column(name = "USER_ID")
	private Long userId;

	@Size(max = 200)
	@NotNull(message = Constants.NOT_NULL_FIELD)
	@NotEmpty(message = Constants.NOT_EMPTY_FIELD)
	@Column(name = "DESCRIPTION")
	private String description;
}
