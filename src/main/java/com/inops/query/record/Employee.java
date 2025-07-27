package com.inops.query.record;


import java.util.List;

public record Employee(
        String organizationCode,
        String contractorCode,
        String tenantCode,
        String employeeID,
        String firstName,
        String middleName,
        String lastName,
        String fatherHusbandName,
        String gender,
        String birthDate,
        String bloodGroup,
        String nationality,
        String maritalStatus,
        String marriageDate,
        String referenceBy,
        String dateOfJoining,
        String contractFrom,
        String contractTo,
        int contractPeriod,
        WorkSkill workSkill,
        String paymentMode,
        BusDetail busDetail,
        NatureOfWork natureOfWork,
        Rejoin rejoin,
        String backgroundVerificationRemark,
        String medicalVerificationRemark,
        EmailID emailID,
        ContactNumber contactNumber,
        CovidVaccine covidVaccine,
        Deployment deployment,
        List<Integer> weeklyOff,
        List<Card> cards,
        List<Document> documents,
        Passport passport,
        WorkPermit workPermit,
        LabourCard labourCard,
        BankDetails bankDetails,
        String insuranceNumber,
        String mediclaimPolicyNumber,
        String WCPolicyNumber,
        String accidentPolicyNumber,
        Address address,
        List<FamilyMember> familyMember,
        HighestEducation highestEducation,
        Experience experience,
        List<Training> trainings,
        List<UploadedDocument> uploadedDocuments,
        List<AssetAllocated> assetAllocated,
        List<Penalty> penalty,
        List<WorkOrder> workOrder,
        List<Nominee> pfNominee,
        List<Nominee> gratuityNominee,
        List<MedicalCheckup> medicalCheckup,
        List<DisciplinaryAction> disciplinaryAction,
        List<PoliceVerification> policeVerification,
        List<AccidentRegister> accidentRegister,
        Status status,
        String remark,
        AuditTrail auditTrail,
        boolean onNoticePeriod,
        String manager,
        String superviser
) {
    public record WorkSkill(String workSkillCode, String workSkillTitle) {
    }

    public record BusDetail(String busNumber, String busRegistrationNumber, String route) {
    }

    public record NatureOfWork(String natureOfWorkCode, String natureOfWorkTitle) {
    }

    public record Rejoin(boolean isRejoining, String oldEmployeeCode) {
    }

    public record EmailID(String primaryEmailID, String secondaryEmailID) {
    }

    public record ContactNumber(
            String primaryContactNo,
            String secondarContactNumber,
            String emergencyContactPerson1,
            String emergencyContactNo1,
            String emergencyContactPerson2,
            String emergencyContactNo2
    ) {
    }

    public record CovidVaccine(
            boolean vaccine1,
            boolean vaccine2,
            boolean vaccine3,
            String vaccine1Certificate,
            String vaccine2Certificate,
            String vaccine3Certificate
    ) {
    }

    public record Deployment(
            Subsidiary subsidiary,
            Division division,
            Department department,
            SubDepartment subDepartment,
            Section section,
            EmployeeCategory employeeCategory,
            Grade grade,
            Designation designation,
            Location location,
            SkillLevel skillLevel,
            Contractor contractor,
            String effectiveFrom,
            String remark
    ) {
        public record Subsidiary(String subsidiaryCode, String subsidiaryName) {
        }

        public record Division(String divisionCode, String divisionName) {
        }

        public record Department(String departmentCode, String departmentName) {
        }

        public record SubDepartment(String subDepartmentCode, String subDepartmentName) {
        }

        public record Section(String sectionCode, String sectionName) {
        }

        public record EmployeeCategory(String employeeCategoryCode, String employeeCategoryTitle) {
        }

        public record Grade(String gradeCode, String gradeTitle) {
        }

        public record Designation(String designationCode, String designationName) {
        }

        public record Location(String locationCode, String locationName) {
        }

        public record SkillLevel(String skilledLevelTitle, String skilledLevelDescription) {
        }

        public record Contractor(String contractorCode, String contractorName) {
        }
    }

    public record Card(
            String cardNumber,
            String effectiveFrom,
            String effectiveTo,
            boolean isPrimaryCard
    ) {
    }

    public record Document(
            String documentTypeCode,
            String documentTypeTitle,
            String identificationNumber,
            String documentPath
    ) {
    }

    public record Passport(
            String passportNumber,
            String passportExpiryDate,
            String passportPath
    ) {
    }

    public record WorkPermit(
            String workpermitNumber,
            String workpermitExpiryDate,
            String workPermitPath
    ) {
    }

    public record LabourCard(
            String labourCardNumber,
            String labourcardExpiryDate,
            String labourCardPath
    ) {
    }

    public record BankDetails(
            String bankName,
            String ifscCode,
            String branchName,
            String accountNumber
    ) {
    }

    public record Address(
            AddressDetails permanentAddress,
            AddressDetails temporaryAddress
    ) {
        public record AddressDetails(
                String addressLine1,
                String addressLine2,
                String country,
                String state,
                String city,
                String pinCode,
                String taluka,
                boolean isVerified
        ) {
        }
    }

    public record FamilyMember(
            String memberName,
            String relation,
            String gender,
            String birthDate,
            AadharCard aadharCard,
            ElectionCard electionCard,
            PanCard panCard,
            String remark,
            boolean isDependent
    ) {
        public record AadharCard(String aadharCardNumber, String aadharCardPath) {
        }

        public record ElectionCard(String electionCardNumber, String electionCardPath) {
        }

        public record PanCard(String panCardNumber, String panCardPath) {
        }
    }

    public record HighestEducation(
            String educationTitle,
            String courseTitle,
            String stream,
            String college,
            String yearOfPassing,
            int monthOfPassing,
            String percentage,
            boolean isVerified
    ) {
    }

    public record Experience(
            String companyName,
            String fromDate,
            String toDate,
            String designation,
            String filePath
    ) {
    }

    public record Training(
            TrainingProgram trainingProgram,
            String fromDate,
            String toDate,
            int totalDays,
            String totalHours,
            String validUpto,
            String conductedByFaculty,
            String conductedByCompany,
            String filePath
    ) {
        public record TrainingProgram(String trainingProgramCode, String trainingProgramTitle) {
        }
    }

    public record UploadedDocument(
            DocumentCategory documentCategory,
            DocumentType documentType,
            String documentPath
    ) {
        public record DocumentCategory(String documentCategoryCode, String documentCategoryTitle) {
        }

        public record DocumentType(String documentTypeCode, String documentTypeTitle) {
        }
    }

    public record AssetAllocated(
            Asset asset,
            String issueDate,
            String returnDate
    ) {
        public record Asset(
                String assetCode,
                String assetName,
                AssetType assetType
        ) {
            public record AssetType(String assetTypeCode, String assetTypeTitle) {
            }
        }
    }

    public record Penalty(
            String dateOfOffence,
            String offenceDescription,
            String actionTaken,
            double fineImposed,
            int month,
            boolean isCauseShownAgainstFine,
            String witnessName,
            String fineRealisedDate
    ) {
    }

    public record WorkOrder(
            String workOrderNumber,
            String effectiveFrom,
            String effectiveTo
    ) {
    }

    public record Nominee(
            String memberName,
            String relation,
            String birthDate,
            String percentage
    ) {
    }

    public record MedicalCheckup(
            String checkupDate,
            String nextCheckupDate,
            String description,
            String documentPath
    ) {
    }

    public record DisciplinaryAction(
            String actionTakenOn,
            String issueReportedOn,
            String issuedescription,
            String actionDescription,
            String remark,
            String status,
            String documentPath
    ) {
    }

    public record PoliceVerification(
            String verificationDate,
            String nextVerificationDate,
            String description,
            String documentPath
    ) {
    }

    public record AccidentRegister(
            String dateOfAccident,
            String dateOfReport,
            String accidentDescription,
            String dateOfReturn
    ) {
    }

    public record Status(
            String currentStatus,
            String resignationDate,
            String relievingDate,
            boolean notToReHire
    ) {
    }

    public record AuditTrail(
            String createdBy,
            String createdOn,
            String updatedBy,
            String updatedOn
    ) {
    }
}
