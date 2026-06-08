package com.agencyflow.crm.user.mapper;

import com.agencyflow.crm.user.dto.UserResponse;
import com.agencyflow.crm.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    UserResponse toResponse(User user);
}
