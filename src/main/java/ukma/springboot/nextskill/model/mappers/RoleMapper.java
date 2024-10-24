package ukma.springboot.nextskill.model.mappers;

import ukma.springboot.nextskill.dto.RoleDto;
import ukma.springboot.nextskill.entities.RoleEntity;
import ukma.springboot.nextskill.model.Role;

public class RoleMapper {

    private RoleMapper() {}

    public static Role toRole(RoleEntity roleEntity) {
        Role role = new Role();
        role.setId(roleEntity.getId());
        role.setTitle(roleEntity.getTitle());
        return role;
    }

    public static RoleEntity toRoleEntity(Role role) {
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(role.getId());
        roleEntity.setTitle(role.getTitle());
        return roleEntity;
    }

    public static RoleDto toRoleDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setId(role.getId());
        roleDto.setTitle(role.getTitle());
        return roleDto;
    }

    public static Role toRole(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getId());
        role.setTitle(roleDto.getTitle());
        return role;
    }
}
