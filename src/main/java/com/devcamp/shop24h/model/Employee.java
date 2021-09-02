package com.devcamp.shop24h.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "employees")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotEmpty(message = "last name can't be empty")
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@NotEmpty(message = "first name can't be empty")
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@NotEmpty(message = "extension can't be empty")
	@Column(name = "extension", nullable = false)
	private String extension;
	
	@NotEmpty(message = "email can't be empty")
	@Email(message = "email should be valid")
	@Column(name = "email", nullable = false)
	private String email;
	
	@NotNull
	@Column(name = "office_code", nullable = false)
	private int officeCode;
	
	@Column(name = "report_to")
	private int reportTo;
	
	@NotEmpty(message = "job title can't be empty")
	@Column(name = "job_title", nullable = false)
	private String jobTitle;

	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(int officeCode) {
		this.officeCode = officeCode;
	}

	public int getReportTo() {
		return reportTo;
	}

	public void setReportTo(int reportTo) {
		this.reportTo = reportTo;
	}

	public String getJobTitle() {
		return jobTitle;
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public Employee(int id, String lastName, String firstName, String extension, String email, int officeCode,
			int reportTo, String jobTitle) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
		this.extension = extension;
		this.email = email;
		this.officeCode = officeCode;
		this.reportTo = reportTo;
		this.jobTitle = jobTitle;
	}
}
