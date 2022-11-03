package apap.tutorial.belajarbelajar.service;

import apap.tutorial.belajarbelajar.model.RoleModel;

import java.util.List;


public interface RoleService {
    List<RoleModel> findAll();
    RoleModel getById(Long id);
}
