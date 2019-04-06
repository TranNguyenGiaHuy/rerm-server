package com.huytran.rerm.repository.core;

import com.huytran.rerm.model.core.ModelCore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface RepositoryCore<T extends ModelCore, ID> extends JpaRepository<T, ID> {

    Iterable<T> findByAvailable(Boolean available);
    Optional<T> findByIdAndAvailable(Long id, Boolean available);

}
