package org.jmp.spring.core.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jmp.spring.core.facade.BookingFacade;
import org.jmp.spring.core.model.impl.UserAccount;
import org.jmp.spring.core.model.impl.UserImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/v1/users")
@Slf4j
@RequiredArgsConstructor
public class UserController
{
    private static final String USERS_VIEW_NAME = "users";
    private static final String USERS_MODEL_NAME = "users";
    private final BookingFacade bookingFacade;

    @GetMapping("/{userId}")
    public ModelAndView getUserById(@PathVariable Long userId) {
        log.info("GET /users/{}", userId);
        ModelAndView modelAndView = new ModelAndView(USERS_VIEW_NAME);
        UserImpl user = bookingFacade.getUserById(userId);
        modelAndView.addObject(USERS_MODEL_NAME, List.of(user));
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, params = "email")
    public ModelAndView getUserByEmail(@RequestParam String email) {
        log.info("GET /users/?email={}", email);
        ModelAndView modelAndView = new ModelAndView(USERS_VIEW_NAME);
        UserImpl user = bookingFacade.getUserByEmail(email);
        modelAndView.addObject(USERS_MODEL_NAME, List.of(user));
        return modelAndView;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET, params = "name")
    public ModelAndView getUsersByName(@RequestParam String name, @RequestParam(defaultValue = "100") int pageSize, @RequestParam(defaultValue = "1") int pageNum) {
        log.info("GET /users/?name={}&pageSize={}&pageNum={}", name, pageSize, pageNum);
        ModelAndView modelAndView = new ModelAndView(USERS_VIEW_NAME);
        List<UserImpl> users = bookingFacade.getUsersByName(name, pageSize, pageNum);
        modelAndView.addObject(USERS_MODEL_NAME, users);
        return modelAndView;
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView createUser(@RequestParam Map<String, String> parameters) {
        log.info("POST /users, parameters: {} ", parameters);
        String name = parameters.get("name");
        String email = parameters.get("email");
        String balanceString = parameters.get("balance");
        Long balance = balanceString != null ? Long.valueOf(balanceString) : null;

        UserImpl user = bookingFacade.createUser(new UserImpl(name, email, new UserAccount(balance)));

        ModelAndView modelAndView = new ModelAndView(USERS_VIEW_NAME);
        modelAndView.addObject(USERS_MODEL_NAME, List.of(user));
        return modelAndView;
    }

    @PatchMapping(value = "/{userId}")
    public ModelAndView updateUser(@PathVariable Long userId, @RequestBody MultiValueMap<String, String> parameters) {
        log.info("PATCH /users/{}, parameters: {}", userId, parameters);
        String name = parameters.getFirst("name");
        String email = parameters.getFirst("email");
        String balanceString = parameters.getFirst("balance");
        Long balance = balanceString != null ? Long.valueOf(balanceString) : null;

        UserImpl user = bookingFacade.updateUser(new UserImpl(userId, name, email, new UserAccount(balance)));

        ModelAndView modelAndView = new ModelAndView(USERS_VIEW_NAME);
        modelAndView.addObject(USERS_MODEL_NAME, List.of(user));
        return modelAndView;
    }

    @DeleteMapping(value = "/{userId}")
    ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        log.info("DELETE /users/{}", userId);
        bookingFacade.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}


