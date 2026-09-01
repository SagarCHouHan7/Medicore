package com.medicore.medicore.comman.mapper;

import com.medicore.medicore.hospital.HospitalProfile;
import com.medicore.medicore.hospital.HospitalProfileCreationDto;
import com.medicore.medicore.hospital.HospitalResponseDto;

public class HospitalMapper {

    public static HospitalResponseDto mapToHospitalResponseDto(HospitalProfile hospitalProfile){
       return new HospitalResponseDto(
               hospitalProfile.getId(),
               hospitalProfile.getHospitalName(),
               hospitalProfile.getRegisteredOn(),
               hospitalProfile.getLicenceNo(),
               hospitalProfile.getHospitalType(),
               hospitalProfile.getAbout(),
               hospitalProfile.getAddress(),
               hospitalProfile.getCity(),
               hospitalProfile.getState(),
               hospitalProfile.getZipCode(),
               hospitalProfile.getPhoneNo(),
               hospitalProfile.getCustomerCareEmail(),
               hospitalProfile.getTiming(),
               hospitalProfile.isVerified(),
               hospitalProfile.isActive(),
               hospitalProfile.isHasEmergencyServices(),
               hospitalProfile.isVerificationSubmitted()
       );
    }

    public static HospitalProfile setHospitalProfile(HospitalProfile hospitalProfile, HospitalProfileCreationDto dto) {
        hospitalProfile.setHospitalName(dto.hospitalName());
        hospitalProfile.setLicenceNo(dto.licenceNo());
        hospitalProfile.setHospitalType(dto.hospitalType());
        hospitalProfile.setAbout(dto.about());
        hospitalProfile.setAddress(dto.address());
        hospitalProfile.setCity(dto.city());
        hospitalProfile.setState(dto.state());
        hospitalProfile.setZipCode(dto.zipCode());
        hospitalProfile.setPhoneNo(dto.phoneNo());
        hospitalProfile.setCustomerCareEmail(dto.customerCareEmail());
        hospitalProfile.setTiming(dto.timing());
        hospitalProfile.setHasEmergencyServices(dto.hasEmergencyServices());
        hospitalProfile.setActive(true);
        hospitalProfile.setVerified(false);
        hospitalProfile.setVerificationSubmitted(false);
        return hospitalProfile;
    }

}
