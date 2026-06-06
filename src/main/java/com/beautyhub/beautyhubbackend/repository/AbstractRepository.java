package com.beautyhub.beautyhubbackend.repository;

import com.beautyhub.beautyhubbackend.domain.AbstractDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface AbstractRepository
        <T extends AbstractDomain>
        extends JpaRepository<T, Long> {
}
