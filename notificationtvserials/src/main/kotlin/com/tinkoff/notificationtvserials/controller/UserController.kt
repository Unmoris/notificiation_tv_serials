package com.tinkoff.notificationtvserials.controller

import com.tinkoff.notificationtvserials.dto.UserDtoResponse
import com.tinkoff.notificationtvserials.exception.AlreadyExistsException
import com.tinkoff.notificationtvserials.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/user")
class UserController(private val userService: UserService) {

    @PostMapping("/add")
    fun addUser(@RequestBody user: UserDtoResponse): ResponseEntity<String> {
        return try {
            userService.addUser(user)
            ResponseEntity(MESSAGE_USER_CREATED, HttpStatus.CREATED)
        } catch (e: AlreadyExistsException) {
            e.printStackTrace()
            ResponseEntity(e.message, HttpStatus.CONFLICT)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            ResponseEntity(MESSAGE_ERROR, HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/update")
    fun updateUser(@RequestBody user: UserDtoResponse): ResponseEntity<String> {
        return try {
            userService.updateUserApi(user)
            ResponseEntity(MESSAGE_USER_UPDATE, HttpStatus.CREATED)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            ResponseEntity(MESSAGE_ERROR, HttpStatus.BAD_REQUEST)
        }
    }

    @GetMapping("{id}/search-serial", params = ["searchString"])
    fun searchSerial(@PathVariable id: Long, @RequestParam searchString: String): ResponseEntity<Any> {
        return try {
            ResponseEntity(userService.searchSerial(id, searchString), HttpStatus.CREATED)
        } catch (e: RuntimeException) {
            e.printStackTrace()
            ResponseEntity(MESSAGE_ERROR, HttpStatus.BAD_REQUEST)
        }
    }



}

