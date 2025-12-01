Feature: This is Negative SignUp feature

  #===========================================================================
  #Test case ID :: TC_UI_Zlaata_Signup
  #===========================================================================
  #ScenarioDescription : User Negative signup scenario
  #============================================================================
  
  
   @TC_UI_Zlaata_Signup_01
Scenario Outline: TC_UI_Zlaata_Signup_01 | User  signup scenario |"<TD_ID>"
    Given User Signup with all valid data
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_01 |
  
  @sanity
  @TC_UI_Zlaata_Signup_02
Scenario Outline: TC_UI_Zlaata_Signup_02 | User left name field Empty |"<TD_ID>"
    Given User left name field Empty
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_02 |

@sanity
   @TC_UI_Zlaata_Signup_03
Scenario Outline: TC_UI_Zlaata_Signup_03 | User left phone number field Empty |"<TD_ID>"
    Given User left phone number field Empty
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_03 |

@sanity
  @TC_UI_Zlaata_Signup_04
Scenario Outline: TC_UI_Zlaata_Signup_04 | User entered name with less than three characters |"<TD_ID>"
    Given User entered name with less than three characters
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_04 |

@sanity
      @TC_UI_Zlaata_Signup_05
Scenario Outline: TC_UI_Zlaata_Signup_05 | User entered phone number with less than 10 digits |"<TD_ID>"
    Given User entered phone number with less than 10 digits
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_05 |

@sanity
      @TC_UI_Zlaata_Signup_06
Scenario Outline: TC_UI_Zlaata_Signup_06 | User entered email with less than 3 characters |"<TD_ID>"
    Given User entered email with less than 3 characters
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_06 |

@sanity
            @TC_UI_Zlaata_Signup_07
Scenario Outline: TC_UI_Zlaata_Signup_07 | User entered name with more than 50 characters |"<TD_ID>"
    Given User entered name with more than 50 characters
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_07 |
              
              @sanity
              @TC_UI_Zlaata_Signup_08
Scenario Outline: TC_UI_Zlaata_Signup_08 | User entered phone number with more than 10 digits |"<TD_ID>"
    Given User entered phone number with more than 10 digits
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_08 |

           @sanity
           @TC_UI_Zlaata_Signup_09
Scenario Outline: TC_UI_Zlaata_Signup_09 | User entered email with more than 50 characters |"<TD_ID>"
    Given User entered email with more than 50 characters
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_09|

                  @sanity
                  @TC_UI_Zlaata_Signup_10
Scenario Outline: TC_UI_Zlaata_Signup_10| User entered email in an invalid format |"<TD_ID>"
    Given User entered email in an invalid format
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_10 |

                    @sanity
                    @TC_UI_Zlaata_Signup_11
Scenario Outline: TC_UI_Zlaata_Signup_11 | User entered phone number with non-numeric characters |"<TD_ID>"
    Given User entered phone number with non-numeric characters
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_11 |
                       
 @sanity                      
@TC_UI_Zlaata_Signup_12
Scenario Outline: TC_UI_Zlaata_Signup_12 | User entered name with special characters |"<TD_ID>"
    Given User entered name with special characters
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_12 |
    
    @sanity  
 @TC_UI_Zlaata_Signup_13
Scenario Outline: TC_UI_Zlaata_Signup_13 | User entered the same phone number as twice |"<TD_ID>"
    Given User entered the same phone number as twice
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_13 |
      
@sanity
@TC_UI_Zlaata_Signup_14
Scenario Outline: TC_UI_Zlaata_Signup_14 | User entered phone number starting with 1,2,3,4,5 |"<TD_ID>"
    Given User entered phone number starting with 1,2,3,4,5
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_14 |

@sanity
@TC_UI_Zlaata_Signup_15
Scenario Outline: TC_UI_Zlaata_Signup_15 | User entered name with numeric characters |"<TD_ID>"
    Given User entered name with numeric characters
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_15 |

                            @TC_UI_Zlaata_Signup_16
Scenario Outline: TC_UI_Zlaata_Signup_16 | User entered phone number with invalid characters |"<TD_ID>"
    Given User entered phone number with invalid characters (e.g., alphabets or symbols)
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_16 |

@sanity
@TC_UI_Zlaata_Signup_17
Scenario Outline: TC_UI_Zlaata_Signup_17 | User entered email with invalid domain (e.g., missing "@" or incorrect domain) |"<TD_ID>"
    Given User entered email with invalid domain (e.g., missing "@" or incorrect domain)
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_17 |

@sanity
@TC_UI_Zlaata_Signup_18
Scenario Outline: TC_UI_Zlaata_Signup_18 | User entered email with multiple '@' symbols |"<TD_ID>"
    Given User entered email with multiple '@' symbols
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_18 |

@sanity
@TC_UI_Zlaata_Signup_19
Scenario Outline: TC_UI_Zlaata_Signup_19 | User entered name with mixed case and special characters |"<TD_ID>"
    Given User entered name with mixed case and special characters
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_19 |


@sanity
  @TC_UI_Zlaata_Signup_20
Scenario Outline: TC_UI_Zlaata_Signup_20 | User left all required fields empty (email is optional) |"<TD_ID>"
    Given User left all required fields empty (email is optional)
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_20 |
     
@sanity 
@TC_UI_Zlaata_Signup_21
Scenario Outline: TC_UI_Zlaata_Signup_21 | User enters invalid OTP |"<TD_ID>"
    Given User enters invalid OTP while login
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_21 |
      

@sanity
     @TC_UI_Zlaata_Signup_22
Scenario Outline: TC_UI_Zlaata_Signup_22 | User enters already verified mail |"<TD_ID>"
    Given User entering same mail id which is already registered
    Examples:
      | TD_ID                  |
      | TD_UI_Zlaata_Signup_22 |

