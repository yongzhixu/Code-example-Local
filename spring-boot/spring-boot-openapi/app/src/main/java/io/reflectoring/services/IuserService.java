package io.reflectoring.services;

import io.reflectoring.entity.UserDb;

import java.util.List;

public interface IuserService {

    UserDb getUserById(Long userId);
}
