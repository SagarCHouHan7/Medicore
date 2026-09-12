package com.medicore.medicore.admin;

import com.medicore.medicore.account.UserRepo;
import com.medicore.medicore.comman.utils.UserUtils;
import com.medicore.medicore.clinic.repository.HospitalRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class AdminService {
    private final HospitalRepo hospitalRepo;
    private final UserUtils userUtils;
    private final UserRepo userRepo;
}
