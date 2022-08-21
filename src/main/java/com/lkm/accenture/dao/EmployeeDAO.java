package com.lkm.accenture.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lkm.accenture.entity.EmployeeEntity;

public interface EmployeeDAO  extends JpaRepository<EmployeeEntity, Integer>{

}
