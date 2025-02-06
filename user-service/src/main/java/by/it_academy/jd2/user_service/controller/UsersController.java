package by.it_academy.jd2.user_service.controller;

import by.it_academy.jd2.user_service.dto.UserCreateDto;
import by.it_academy.jd2.user_service.dto.UserDto;
import by.it_academy.jd2.user_service.service.api.IUserService;
import by.it_academy.lib.dto.PageDto;
import by.it_academy.lib.dto.PaginationDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UsersController {

    private final IUserService userService;

    public UsersController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Valid @RequestBody UserCreateDto userCreatedto) {
        userService.create(userCreatedto);
    }

    @GetMapping
    public PageDto<UserDto> getPage(@Valid PaginationDto paginationDto) {
        return userService.getUsersPage(paginationDto.getPage(), paginationDto.getSize());
    }

    @GetMapping("/{uuid}")
    public UserDto get(@PathVariable("uuid") UUID uuid) {
        return userService.getById(uuid);
    }

    @PutMapping("/{uuid}/dt_update/{dt_update}")
    public void update(@PathVariable("uuid") UUID uuid,
                       @PathVariable("dt_update") long dtUpdate,
                       @Valid @RequestBody UserCreateDto userCreateDTto) {
        userService.update(uuid, dtUpdate, userCreateDTto);
    }


}
