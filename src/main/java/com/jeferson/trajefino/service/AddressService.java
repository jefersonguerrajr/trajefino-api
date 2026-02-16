package com.jeferson.trajefino.service;

import com.jeferson.trajefino.exception.ResourceNotFoundException;
import com.jeferson.trajefino.model.Address;
import com.jeferson.trajefino.model.AddressDTO;
import com.jeferson.trajefino.model.User;
import com.jeferson.trajefino.repository.AddressRepository;
import com.jeferson.trajefino.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<List<Address>> findAddressesByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("Usuário não encontrado com ID: " + userId);
        }
        return ResponseEntity.ok(addressRepository.findByUserId(userId));
    }

    public ResponseEntity<Address> findAddressById(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com ID: " + id));
        return ResponseEntity.ok(address);
    }

    public ResponseEntity<Address> findDefaultAddress(Long userId) {
        Address address = addressRepository.findByUserIdAndIsDefaultTrue(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum endereço padrão encontrado para o usuário"));
        return ResponseEntity.ok(address);
    }

    @Transactional
    public ResponseEntity<Address> createAddress(AddressDTO addressDTO) throws Exception {
        validateAddressDTO(addressDTO, true);

        User user = userRepository.findById(addressDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado com ID: " + addressDTO.getUserId()));

        // Se for endereço padrão, remove o padrão dos outros endereços do usuário
        if (addressDTO.getIsDefault() != null && addressDTO.getIsDefault()) {
            removeDefaultFromUserAddresses(addressDTO.getUserId());
        }

        Address address = Address.builder()
                .street(addressDTO.getStreet())
                .number(addressDTO.getNumber())
                .complement(addressDTO.getComplement())
                .neighborhood(addressDTO.getNeighborhood())
                .city(addressDTO.getCity())
                .state(addressDTO.getState())
                .zipCode(addressDTO.getZipCode())
                .country(addressDTO.getCountry())
                .addressType(addressDTO.getAddressType())
                .isDefault(addressDTO.getIsDefault() != null ? addressDTO.getIsDefault() : false)
                .user(user)
                .build();

        return ResponseEntity.ok(addressRepository.save(address));
    }

    @Transactional
    public ResponseEntity<Address> updateAddress(Long id, AddressDTO addressDTO) throws Exception {
        validateAddressDTO(addressDTO, true);

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com ID: " + id));

        // Se for endereço padrão, remove o padrão dos outros endereços do usuário
        if (addressDTO.getIsDefault() != null && addressDTO.getIsDefault() && !address.getIsDefault()) {
            removeDefaultFromUserAddresses(address.getUser().getId());
        }

        address.setStreet(addressDTO.getStreet());
        address.setNumber(addressDTO.getNumber());
        address.setComplement(addressDTO.getComplement());
        address.setNeighborhood(addressDTO.getNeighborhood());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setZipCode(addressDTO.getZipCode());
        address.setCountry(addressDTO.getCountry());
        address.setAddressType(addressDTO.getAddressType());
        address.setIsDefault(addressDTO.getIsDefault() != null ? addressDTO.getIsDefault() : false);

        return ResponseEntity.ok(addressRepository.save(address));
    }

    @Transactional
    public ResponseEntity<Address> partialUpdateAddress(Long id, AddressDTO addressDTO) throws Exception {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com ID: " + id));

        // Se for endereço padrão, remove o padrão dos outros endereços do usuário
        if (addressDTO.getIsDefault() != null && addressDTO.getIsDefault() && !address.getIsDefault()) {
            removeDefaultFromUserAddresses(address.getUser().getId());
        }

        // Atualiza apenas os campos que foram fornecidos
        if (addressDTO.getStreet() != null && !addressDTO.getStreet().trim().isEmpty()) {
            address.setStreet(addressDTO.getStreet());
        }
        if (addressDTO.getNumber() != null) {
            address.setNumber(addressDTO.getNumber());
        }
        if (addressDTO.getComplement() != null) {
            address.setComplement(addressDTO.getComplement());
        }
        if (addressDTO.getNeighborhood() != null && !addressDTO.getNeighborhood().trim().isEmpty()) {
            address.setNeighborhood(addressDTO.getNeighborhood());
        }
        if (addressDTO.getCity() != null && !addressDTO.getCity().trim().isEmpty()) {
            address.setCity(addressDTO.getCity());
        }
        if (addressDTO.getState() != null && !addressDTO.getState().trim().isEmpty()) {
            if (addressDTO.getState().length() != 2) {
                throw new Exception("Estado deve ter 2 caracteres");
            }
            address.setState(addressDTO.getState());
        }
        if (addressDTO.getZipCode() != null && !addressDTO.getZipCode().trim().isEmpty()) {
            address.setZipCode(addressDTO.getZipCode());
        }
        if (addressDTO.getCountry() != null && !addressDTO.getCountry().trim().isEmpty()) {
            address.setCountry(addressDTO.getCountry());
        }
        if (addressDTO.getAddressType() != null && !addressDTO.getAddressType().trim().isEmpty()) {
            address.setAddressType(addressDTO.getAddressType());
        }
        if (addressDTO.getIsDefault() != null) {
            address.setIsDefault(addressDTO.getIsDefault());
        }

        return ResponseEntity.ok(addressRepository.save(address));
    }

    @Transactional
    public ResponseEntity<Void> deleteAddress(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com ID: " + id));

        addressRepository.delete(address);
        return ResponseEntity.noContent().build();
    }

    @Transactional
    public ResponseEntity<Address> setDefaultAddress(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Endereço não encontrado com ID: " + id));

        // Remove o padrão dos outros endereços do usuário
        removeDefaultFromUserAddresses(address.getUser().getId());

        // Define este como padrão
        address.setIsDefault(true);
        return ResponseEntity.ok(addressRepository.save(address));
    }

    private void removeDefaultFromUserAddresses(Long userId) {
        List<Address> userAddresses = addressRepository.findByUserId(userId);
        for (Address addr : userAddresses) {
            if (addr.getIsDefault()) {
                addr.setIsDefault(false);
                addressRepository.save(addr);
            }
        }
    }

    private void validateAddressDTO(AddressDTO addressDTO, boolean isRequired) throws Exception {
        if (isRequired) {
            if (addressDTO.getStreet() == null || addressDTO.getStreet().trim().isEmpty()) {
                throw new Exception("Rua é obrigatória");
            }
            if (addressDTO.getCity() == null || addressDTO.getCity().trim().isEmpty()) {
                throw new Exception("Cidade é obrigatória");
            }
            if (addressDTO.getState() == null || addressDTO.getState().trim().isEmpty()) {
                throw new Exception("Estado é obrigatório");
            }
            if (addressDTO.getState().length() != 2) {
                throw new Exception("Estado deve ter 2 caracteres");
            }
            if (addressDTO.getZipCode() == null || addressDTO.getZipCode().trim().isEmpty()) {
                throw new Exception("CEP é obrigatório");
            }
            if (addressDTO.getUserId() == null) {
                throw new Exception("ID do usuário é obrigatório");
            }
        }
    }
}
