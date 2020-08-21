package org.rupali.employeeCrudApplication.entity;

import javax.persistence.*;

import java.sql.Date;
import java.util.UUID;

@Entity
@Table(name = "employee_uuid")
public class Employee {

	@Column
	@Id
	private UUID id = UUID.randomUUID();
	
	@Column
	private String name;
	@Column
	private String gender;
	@Column
	private String department;
	

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", gender=" + gender + ", department=" + department + "]";
	}


	
	
	
}
