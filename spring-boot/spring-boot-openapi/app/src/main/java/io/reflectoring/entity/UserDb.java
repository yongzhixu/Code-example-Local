package io.reflectoring.entity;

/**
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table userstest
 */
public class UserDb {
    public UserDb() {
    }

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userstest.id
     *
     * @mbg.generated
     */
    private Long id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userstest.username
     *
     * @mbg.generated
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userstest.first_name
     *
     * @mbg.generated
     */
    private String firstName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column userstest.last_name
     *
     * @mbg.generated
     */
    private String lastName;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userstest.id
     *
     * @return the value of userstest.id
     *
     * @mbg.generated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userstest.id
     *
     * @param id the value for userstest.id
     *
     * @mbg.generated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userstest.username
     *
     * @return the value of userstest.username
     *
     * @mbg.generated
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userstest.username
     *
     * @param username the value for userstest.username
     *
     * @mbg.generated
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userstest.first_name
     *
     * @return the value of userstest.first_name
     *
     * @mbg.generated
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userstest.first_name
     *
     * @param firstName the value for userstest.first_name
     *
     * @mbg.generated
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column userstest.last_name
     *
     * @return the value of userstest.last_name
     *
     * @mbg.generated
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column userstest.last_name
     *
     * @param lastName the value for userstest.last_name
     *
     * @mbg.generated
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}