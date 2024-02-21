package com.mango.data.repository;

import com.mango.model.entity.SloganEntity;
import com.mango.model.util.Constants;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface SloganRepository extends JpaRepository<SloganEntity, Long> {

	List<SloganEntity> findByUserId(@NotNull(message = Constants.NOT_NULL_FIELD) @NotEmpty(message = Constants.NOT_EMPTY_FIELD) Long userId);
}
