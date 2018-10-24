//Unified API - Users.java

package com.qa.data;
/// define the users data in the below class. It contains the data that we pass through JSON/POJO(plain old java object)--
public class Users {
	
	String agreementType;
	String opType;
	String basicCustomerInfo;		//Nested
	String firstName;
	String fullName;
	String lastName;
	String registrationDate;
	String createActiveCustomer;
	String contacts;		//Nested
	String contact; 		//Nested
	String status;
	String type;
	String validFrom;
	String validUpto;
	String value;
	
	String customerIdInfo; 		//Nested
	String customerIds; 		//Nested
	String idType;
	String idValue;
	
	String customerPermissionInfo; 		//Nested
	String customerFreeze;
	String freezeFrom;
	
	String customerPropInfo; 		//Nested
	String customerStatuses; 		//Nested
	String customerStatus; 			//Nested
	String judgementDate;
	String judgementDescription;
	String registrationSource;
	String sourceKey;
	
//Create constructor#1	
	public Users(){		
	}

//Create constructor#2	
	public Users(String agreementType, String opType){
		this.agreementType = agreementType;
		this.opType = opType;	
		this.firstName = firstName;
		this.fullName = fullName;
		this.lastName = lastName;
		this.registrationDate = registrationDate;
		this.createActiveCustomer = createActiveCustomer;	
		this.status = status;
		this.type = type;
		this.validFrom = validFrom;
		this.validUpto = validUpto;
		this.value = value;	
		this.idType = idType;
		this.idValue = idValue;
		this.customerFreeze = customerFreeze;
		this.freezeFrom = freezeFrom;	
		this.judgementDate = judgementDate;
		this.judgementDescription = judgementDescription;
		this.registrationSource = registrationSource;
		this.sourceKey = sourceKey;	
	}

	public String getAgreementType() {
		return agreementType;
	}

	public void setAgreementType(String agreementType) {
		this.agreementType = agreementType;
	}

	public String getOpType() {
		return opType;
	}

	public void setOpType(String opType) {
		this.opType = opType;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getCreateActiveCustomer() {
		return createActiveCustomer;
	}

	public void setCreateActiveCustomer(String createActiveCustomer) {
		this.createActiveCustomer = createActiveCustomer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}

	public String getValidUpto() {
		return validUpto;
	}

	public void setValidUpto(String validUpto) {
		this.validUpto = validUpto;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdValue() {
		return idValue;
	}

	public void setIdValue(String idValue) {
		this.idValue = idValue;
	}

	public String getCustomerFreeze() {
		return customerFreeze;
	}

	public void setCustomerFreeze(String customerFreeze) {
		this.customerFreeze = customerFreeze;
	}

	public String getFreezeFrom() {
		return freezeFrom;
	}

	public void setFreezeFrom(String freezeFrom) {
		this.freezeFrom = freezeFrom;
	}

	public String getJudgementDate() {
		return judgementDate;
	}

	public void setJudgementDate(String judgementDate) {
		this.judgementDate = judgementDate;
	}

	public String getJudgementDescription() {
		return judgementDescription;
	}

	public void setJudgementDescription(String judgementDescription) {
		this.judgementDescription = judgementDescription;
	}

	public String getRegistrationSource() {
		return registrationSource;
	}

	public void setRegistrationSource(String registrationSource) {
		this.registrationSource = registrationSource;
	}

	public String getSourceKey() {
		return sourceKey;
	}

	public void setSourceKey(String sourceKey) {
		this.sourceKey = sourceKey;
	}

//create getters and setters methods	
	
		
} // class close

