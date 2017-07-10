(deffacts startup (menu-op start))

(defrule main-menu
	(menu-op start)
	?fact <- (menu-op start)
	=>
	(printout t t t t "*****BOEING 747 FAULT ISOLATION EXPERT SYSTEM*****" crlf)
	(printout t t "MAIN MENU" t crlf)
	(printout t "1 - Oil consumption is high" crlf)
	(printout t "2 - Oil quantity indicator is malfunctioning" crlf)
	(printout t "3 - Oil pressure is abnormal or indicator is malfunctioning" crlf)
	(printout t "4 - Oil filter bypass light is illuminated" crlf)
	(printout t "5 - Oil temperature is abnormal or indicator is malfunctioning" crlf)
	(printout t "6 - Breather temperature is high" crlf)
	(printout t "7 - Engine was shutdown in flight" crlf)
	(printout t "8 - Unlisted engine oil fault" crlf)
	(printout t t "Which of the above were observed during the flight? >" )
	(assert (observed-problem-number (read)) )
	(printout t crlf)
	(retract ?fact)
)

(defrule engine-number
	(menu-op engine-num)
	?fact <- (menu-op engine-num)
	=>
	(printout t "Which engine is malfuctioning? (1,2,3,4,0) >")
	(assert (engine-num (read)))
	(printout t crlf)
	(retract ?fact)
)

;------------------------------------------------------------------------------------------------------

(defrule oil-consumption
	(observed-problem-number 1)
	=>
	(assert (observed-problem-name high_consumption))
	(assert (menu-op engine-num))
)

(defrule oil-qty-ind
	(observed-problem-number 2)
	=>
	(printout t "possible fault not listed")
	(printout t crlf)
)

(defrule oil-pressure
	(observed-problem-number 3)
	=>
	(printout t "possible fault not listed")
	(printout t crlf)
)


(defrule oil-filter-bypass
	(observed-problem-number 4)
	=>
	(printout t "possible fault not listed")
	(printout t crlf)
)

(defrule oil-temperature
	(observed-problem-number 5)
	=>
	(assert (observed-problem-name oil_temp))
	(assert (menu-op engine-num))
)

(defrule breather-temperature
	(observed-problem-number 6)
	=>
	(printout t "possible fault not listed")
	(printout t crlf)
)

(defrule shutdown-in-flight
	(observed-problem-number 7)
	=>
	(printout t "possible fault not listed")
	(printout t crlf)
)

(defrule not_listed
	(observed-problem-number 8)
	=>
	(printout t "possible fault not listed")
	(printout t crlf)
)

;------------------------------------------------------------------------------------------------------

(defrule high-oil-consumption
	(observed-problem-name high_consumption)
	(engine-num ?entry)
	=>
	(assert (error-code (sym-cat 79-01-BA-0 ?entry)))
	(printout t "Are there any other abnormal oil systems? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (error-code (sym-cat 79-01-BE)))
		else (assert (error-code (sym-cat 79-01-BD))) 
	)
)

(defrule BD-BE 
	(or (error-code 79-01-BE) (error-code 79-01-BD))
	=>
	(printout t "Examine turbine exhaust area for evidence of oil loss per Visual Check 1, 79-01-10.  Is oil loss occuring? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (bdbe 2))
		else (assert (bdbe 1))
	)
)

(defrule bdbe1
	(bdbe 1)
	?fact <- (bdbe 1)
	=>
	(printout t "Examine main gearbox drains (MM 71-71-00) for leakage.  Is excessive oil present? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (bdbe 14))
		else (assert (bdbe 15))
	)
	(retract ?fact)
)

(defrule bdbe2
	(bdbe 2)
	?fact <- (bdbe 2)
	=>
	(printout t "Identify source of oil loss per Visual Check 1, 79-01-10.  Is oil loss due to leakage from rear cover of No. 4 bearing compartment? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (bdbe 4))
		else (assert (bdbe 3))
	)
	(retract ?fact)
)

(defrule bdbe3
	(bdbe 3)
	?fact <- (bdbe 3)
	=>
	(printout t "Is oil loss due to leakage from oil pressure supply line or oil scavenge line of No. 4 bearing compartment? [yes,no] >" crlf )
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (bdbe 5))
		else (assert (bdbe 6))
	)
	(retract ?fact)
)

(defrule bdbe4or9
	(or (bdbe 4) (bdbe 9))
	?fact <- (bdbe ?)
	=>
	(printout t " Replace engine.  MM 71-00-02 ",crlf )
	(retract ?fact)
)

(defrule bdbe5
	(bdbe 5)
	?fact <- (bdbe 5)
	=>
	(printout t " Remove and clean or replace oil pressure supply tube and/or oil scavenge tube as required.  MM 72-53-00." crlf)
	(retract ?fact)
)

(defrule bdbe6
	(bdbe 6)
	?fact <- (bdbe 6)
	=>
	(printout t "Is oil loss due to a clogged or loose oil scavenge line or a failed scavenge pump? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (bdbe 8))
		else (assert (bdbe 9))
	)
	(retract ?fact)
)

(defrule bdbe8
	(bdbe 8)	
	?fact <- (bdbe 8)
	=>
	(printout t " Remove and clean or replace oil scavenge tube as necessary.  MM 72-53-00.  Replace scavenge pump if required.  MM 72-61-21." crlf )
	(retract ?fact)
)

(defrule bdbe14
	(bdbe 14)
	?fact <- (bdbe 14)
	=>
	(printout t "Identify leaking drain line source (MM 71-71-00) Was source of leakage from the fuel/oil cooler? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (bdbe 16))
		else (assert (bdbe 17))
	)
	(retract ?fact)
)

(defrule bdbe15
	(bdbe 15)
	?fact <- (bdbe 15)
	=>
	(printout t "Check that PT3 water drain plug is installed per Visual Check 9, 71-01-10.  Is plug missing? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (bdbe 19A))
		else (assert (bdbe 19B))
	)
	(retract ?fact)
)

(defrule bdbe16
	(bdbe 16)
	?fact <- (bdbe 16)
	=>
	(printout t " Replace fuel/oil cooler (MM 79-21-01)." ,crlf)
	(retract ?fact)
)

(defrule bdbe17
	(bdbe 17)
	?fact <- (bdbe 16)
	=>
	(printout t " Remove applicable component and check both component and drive pad seal.  Replace component and/or drive seal as follows:",crlf)
	(printout t "        Component           Seal Replacement Ref",crlf)
	(printout t "        ---------           --------------------",crlf)
	(printout t " Generator (MM 24-21-01)        MM 72-61-08",crlf)
	(printout t " Fuel Pump (MM 73-11-01)        MM 72-61-11",crlf)
	(printout t " Hydraulic Pump (MM 29-11-05)   MM 72-61-09",crlf)
	(printout t " Starter (MM 80-11-01)          MM 72-61-06",crlf)
	(printout t " Constant Speed Drive           MM 72-61-07",crlf)
	(printout t " (MM 24-11-01)",crlf)
	(retract ?fact)
)

(defrule bdbe19A
	(bdbe 19A)
	?fact <- (bdbe 19A)
	=>
	(printout t "Install drain plug." ,crlf)
	(retract ?fact)
)

(defrule bdbe19B
	(bdbe 19B)
	?fact <- (bdbe 19B)
	=>
	(printout t "Examine external plumbing, main gearbox and angle gearbox for obvious leakage per Visual Check 2, 79-01-10.  Is obvious leakage present? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (bdbe 20))
		else (assert (bdbe 21))
	)
	(retract ?fact)
)

(defrule bdbe20
	(bdbe 20)
	?fact <- (bdbe 20)
	=>
	(printout t "Is leakage from oil pressure and/or oil scavenge lines? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (bdbe 22))
		else (assert (bdbe 23))
	)
	(retract ?fact)
)

(defrule bdbe22
	(bdbe 22)
	?fact <- (bdbe 22)
	=>
	(printout t "Is leakage from No. 3 bearing oil scavenge tube connections? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (bdbe 24))
		else (assert (bdbe 25))
	)
	(retract ?fact)
)

(defrule bdbe23
	(bdbe 23)
	?fact <- (bdbe 23)
	=>
	(printout t "Is leakage from breather lines? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (bdbe 28))
		else (assert (bdbe 29))
	)
	(retract ?fact)
)

(defrule bdbe28
	(bdbe 28)
	?fact <- (bdbe 28)
	=>
	(printout t "Is leakage from No. 1 and 2 bearing breather manifold and/or No. 3 bearing breather manifold? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (bdbe 30))
		else (assert (bdbe 31))
	)
	(retract ?fact)
)

(defrule bdbe24
	(bdbe 24)
	?fact <- (bdbe 24)
	=>
	(printout t "Repair No. 3 bearing oil scavenge tube connections as required.  MM 79-21-03 AR." crlf)
	(retract ?fact)
)

(defrule bdbe24
	(bdbe 24)
	?fact <- (bdbe 24)
	=>
	(printout t "Repair No. 3 bearing oil scavenge tube connections as required.  MM 79-21-03 AR." crlf)
	(retract ?fact)
)

(defrule bdbe28or31
	(or (bdbe 28) (bdbe 31))
	?fact <- (bdbe ?)
	=>
	(printout t "Replace Engine.  MM 71-00-02" crlf)
	(retract ?fact)
)

(defrule bdbe30
	(bdbe 30)
	?fact <- (bdbe 30)
	=>
	(printout t "Replace No. 1 and 2 bearing breather manifold and/or No. 3 bearing breather manifold as required.  MM 79-21-04 R/I." crlf)
	(retract ?fact)
)

(defrule bdbe29
	(bdbe 29)
	?fact <- (bdbe 29)
	=>
	(printout t "Is leakage from oil instrumentation lines? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (bdbe 33))
		else (assert (bdbe 34))
	)
	(retract ?fact)
)

(defrule bdbe34
	(bdbe 34)
	?fact <- (bdbe 34)
	=>
	(printout t "Is leakage from N2 manual crank on main gearbox? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (bdbe 35))
		else (assert (bdbe 36))
	)
	(retract ?fact)
)

(defrule bdbe36
	(bdbe 36)
	?fact <- (bdbe 36)
	=>
	(printout t "Is leakage from angle gearbox? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (bdbe 38))
		else (assert (bdbe 39))
	)
	(retract ?fact)
)

(defrule bdbe39
	(bdbe 39)
	?fact <- (bdbe 39)
	=>
	(printout t "Is leakage from main gearbox? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (bdbe 41))
		else (assert (bdbe 42))
	)
	(retract ?fact)
)

(defrule bdbe33
	(bdbe 33)
	?fact <- (bdbe 33)
	=>
	(printout t "Replace Engine. MM 71-00-02" crlf)
	(retract ?fact)
)

(defrule bdbe35
	(bdbe 35)
	?fact <- (bdbe 35)
	=>
	(printout t "Remove N2 manual crank pad and install new o-ring and gasket (if applicable).  MM 72-00-00 MP." crlf)
	(retract ?fact)
)

(defrule bdbe38
	(bdbe 38)
	?fact <- (bdbe 38)
	=>
	(printout t "Replace angle gearbox.  MM 72-61-01 R/I." crlf)
	(retract ?fact)
)

(defrule bdbe41
	(bdbe 41)
	?fact <- (bdbe 41)
	=>
	(printout t "Replace main gearbox.  MM 72-61-02 R/I." crlf)
	(retract ?fact)
)

(defrule bdbe42
	(bdbe 42)
	?fact <- (bdbe 42)
	=>
	(printout t "Replace Engine.  MM 71-00-02" crlf)
	(retract ?fact)
)

(defrule bdbe21
	(bdbe 21)
	?fact <- (bdbe 21)
	=>
	(printout t "Perform oil system static leak check per Engine Check 1, 79-01-20 and/or oil system monitoring leak check per Engine Check 2, 79-01-20.  Was source of leakage found? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (bdbe 45))
		else (assert (bdbe 46))
	)
	(retract ?fact)
)

(defrule bdbe46
	(bdbe 46)
	?fact <- (bdbe 46)
	=>
	(printout t "Check fuel pump hydraulic stage pressure per Engine Check 2, 71-01-20.  Is pressure within limits? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (bdbe 49))
		else (assert (bdbe 48))
	)
	(retract ?fact)
)

(defrule bdbe49
	(bdbe 49)
	?fact <- (bdbe 49)
	=>
	(printout t "Check ground idle speed.  MM 71-00-00 A/T, Test No. 9.  Is ground idle speed low? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (bdbe 51))
		else (assert (bdbe 52))
	)
	(retract ?fact)
)

(defrule bdbe52
	(bdbe 52)
	?fact <- (bdbe 52)
	=>
	(printout t ">From idle power, advance thrust level slowly to increase N2 RPM by 10+ACU-.  Did N1 increase at least 10+ACU- also? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (bdbe 54))
		else (assert (bdbe 55))
	)
	(retract ?fact)
)

(defrule bdbe45
	(bdbe 45)
	?fact <- (bdbe 45)
	=>
	(printout t "Refer to Engine Check 1 and/or engine check 2 for corrective action." crlf)
	(retract ?fact)
)

(defrule bdbe48
	(bdbe 48)
	?fact <- (bdbe 48)
	=>
	(printout t "Replace fuel pump.  MM 73-11-01." crlf)
	(retract ?fact)
)

(defrule bdbe51
	(bdbe 51)
	?fact <- (bdbe 51)
	=>
	(printout t "Adjust ground idle speed.  MM 71-00-00 A/T, Test No. 9." crlf)
	(retract ?fact)
)

(defrule bdbe54
	(bdbe 54)
	?fact <- (bdbe 54)
	=>
	(printout t "Replace Evc.  MM 75-31-01." crlf)
	(retract ?fact)
)

(defrule bdbe55
	(bdbe 55)
	?fact <- (bdbe 55)
	=>
	(printout t "AThe following are infrequent causes of this fault:
 1. Faulty main gearbox deaerator          Ref Engine Check 3, 79-01-20 for resolution
 2. PT3 manifold leaks                     Ref Visual Check 8, 71-01-10 for resolution
 3. No. 1 and 2 bearing compartment leaks  Replace Engine (MM 71-00-02)" crlf)
	(retract ?fact)
)

;------------------------------------------------------------------------------------------------

(defrule abnormal-oil-temperature
	(observed-problem-name oil_temp)
	(engine-num ?entry)
	=>
	(assert (error-code (sym-cat 79-01-BA-0 ?entry)))
	(printout t "Is flight engineer's panel oil temperature abnormal? [yes,no] >" crlf)
	(bind ?answer1 (read))
	(printout t "Is flight engineer's panel oil temperature abnormally high? [yes,no] >" crlf)
	(bind ?answer2 (read))
	(if (and (eq ?answer1 yes) (eq ?answer2 yes))
		then (assert (error-code (sym-cat 79-01-DF)))
		else  (printout t "possible fault not listed" crlf)
	)
)

(defrule DF
	(error-code 79-01-DF)
	=>
	(printout t "Open eng oil temp and press circuit breaker on P6 panel.  Observe oil temperature indication.  Does indicator drive below -40 C? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (df 1))
		else (assert (df 2))
	)
)

(defrule df1
	(df 1)
	?fact <- (df 1)
	=>
	(printout t "Disconnect oil temperature bulb electrical connector, T140.  Observe oil temperature indication.  Does indicator drive above +170- C? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (df 12))
		else (assert (df 9))
	)
	(retract ?fact)
)

(defrule df9
	(df 9)
	?fact <- (df 9)
	=>
	(printout t "Replace oil temperature indicator N18, N19, N20, or N21.  MM 79-34-02.  Observe oil temperature indication.  Does indicator drive above +170- C? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer no)
		then (assert (df 10))
	)
	(retract ?fact)
)

(defrule df12
	(df 12)
	?fact <- (df 12)
	=>
	(printout t "Attach decade resistor to pins 1 and 3 of electrical plug disconnected from oil temperature bulb.  Set decade resistor to the following values:
  Decade Resistor Ohms         Indicator Reading
  --------------------         -----------------
          129                   100  2 deg C
          147                   140  4 deg C
          162                   170  4 deg C
Observe oil temperature indication.  Does indicator reading correspond to listed values? [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (df 17))
		else (assert (df 16))
	)
	(retract ?fact)
)

(defrule df2
	(df 2)
	?fact <- (df 2)
	=>
	(printout t "Replace oil temperature indicator N18, N19, N20, or N21.  MM 79-34-02." crlf)
	(retract ?fact)
)

(defrule df10
	(df 10)
	?fact <- (df 10)
	=>
	(printout t "Repair electrical wiring between oil temperature bulb electrical connector and oil temperature indicator.  WM 79-34-11." crlf)
	(retract ?fact)
)

(defrule df16
	(df 16)
	?fact <- (df 16)
	=>
	(printout t "Replace oil temperature indicator N18, N19, N20, or N21.  MM 79-34-02.  Observe oil temperature indication.  Does indicator drive above +170- C?
Attach decade resistor to pins 1 and 3 of electrical plug disconnected from oil temperature bulb.  Set decade resistor to the following values:
  Decade Resistor Ohms         Indicator Reading
  --------------------         -----------------
          129                   100  2 deg C
          147                   140  4 deg C
          162                   170  4 deg C
Observe oil temperature indication.  Does indicator reading correspond to listed values?
 [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer no)
		then (assert (df 22))
	)
	(retract ?fact)
)

(defrule df17
	(df 17)
	?fact <- (df 17)
	=>
	(printout t "Measure temperature of fuel/oil cooler adjacent to oil temperature bulb.  Measure resistance between electrical terminals on bulb T426.  Resistance should be as follows:
  Temperature (C)         Resistance (Ohms)
  ---------------         -----------------
       -20                  83.77  0.40
       -10                  87.04  0.40
         0                  90.38  0.40
        10                  93.80  0.40
        20                  97.31  0.40
        30                 100.91  0.40
        40                 104.60  0.40
        50                 108.39  0.40
        60                 112.28  0.50
        70                 116.27  0.50
Interpolate resistance values for temperatures between those given.  Do temperature and resistance values agree?  [yes,no] >" crlf)
	(bind ?answer (read))
	(if (eq ?answer yes)
		then (assert (df 27))
		else (assert (df 26))
	)
	(retract ?fact)
)

(defrule df22
	(df 22)
	?fact <- (df 22)
	=>
	(printout t "Repair electrical wiring between oil temperature bulb electrical connector T426 and oil temperature indicator.  WM 79-34-11." crlf)
	(retract ?fact)
)

(defrule df26
	(df 26)
	?fact <- (df 26)
	=>
	(printout t "Replace oil temperature bulb T426.  MM 79-34-01." crlf)
	(retract ?fact)
)

(defrule df27
	(df 27)
	?fact <- (df 27)
	=>
	(printout t "Replace fuel/oil cooler flow control thermostat (bypass valve).  MM 79-21-05." crlf)
	(retract ?fact)
)