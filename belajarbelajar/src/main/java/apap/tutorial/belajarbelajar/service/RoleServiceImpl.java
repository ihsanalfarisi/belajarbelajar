package apap.tutorial.belajarbelajar.service;

import apap.tutorial.belajarbelajar.model.RoleModel;
import apap.tutorial.belajarbelajar.repository.RoleDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    RoleDb roleDb;

    @Override
    public List<RoleModel> findAll() {
        return roleDb.findAll();
    }

    @Override
    public RoleModel getById(Long id) {
        return roleDb.findById(id).get();
    }
}
