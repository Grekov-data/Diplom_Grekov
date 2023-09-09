package org.datko.diplom_grekov.rdb;

import lombok.RequiredArgsConstructor;
import org.datko.diplom_grekov.entity.Company;
import org.datko.diplom_grekov.rdb.repository.CompanyRepository;
import org.datko.diplom_grekov.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class RdbCompanyRepository implements CompanyService {

    private final CompanyRepository companyRepository;
    @Override
    public Iterable<Company> findAll() {
        return companyRepository.findAll();
    }

    @Override
    public Optional<Company> findById(Integer id) {
        return companyRepository.findById(id);
    }

    @Override
    public Optional<Company> findByName(String name) {
        return companyRepository.findByName(name);
    }

    @Override
    public Optional<Company> add(Company company) {
        if (companyRepository.findByName(company.getName()).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(companyRepository.save(company));
    }

    @Override
    public Optional<Company> deleteById(Integer id) {
        Optional<Company> removable = findById(id);
        if (removable.isPresent()) {
            companyRepository.deleteById(id);
        }
        return removable;
    }

    @Override
    public Optional<Company> deleteByName(String name) {
        Optional<Company> removable = findByName(name);
        if (removable.isPresent()) {
            companyRepository.deleteByName(name);
        }
        return removable;
    }

    @Override
    public Optional<Company> updateById(Integer id, Company company) {
        Optional<Company> updated = findById(id);
        if (updated.isPresent()) {
            company.setId(id);
            return Optional.of(companyRepository.save(company));
        }
        return updated;
    }

    @Override
    public Optional<Company> updateByName(String name, Company company) {
        Optional<Company> updated = findByName(name);
        if (updated.isPresent()) {
            company.setName(name);
            return Optional.of(companyRepository.save(company));
        }
        return updated;
    }
}
