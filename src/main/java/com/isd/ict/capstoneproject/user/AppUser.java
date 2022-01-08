package com.isd.ict.capstoneproject.user;

import com.isd.ict.capstoneproject.rental.Rental;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
/**
 * The {@link AppUser appUser} interface provide functionalities for user object.
 *
 * @author Group 3
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AppUser {
    /**
     * app user id
     */
    private int appUserId;
    /**
     * name
     */
    private String name;
    /**
     * password
     */
    private String password;
    /**
     * rental history
     */
    private List<Rental> rentalHistory;

    public AppUser(int appUserId) {
        this.appUserId = appUserId;
    }
}
