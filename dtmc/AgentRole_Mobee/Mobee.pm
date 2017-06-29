dtmc

formula noError = true & sG8_T1 < 4 & sG9_T1_1 < 4 & sG9_T1_2 < 4 & sG4_T1 < 4;


const double rTaskG8_T1=0.99;
const double maxRetriesG8_T1=3;

module G8_T1_ProcessModification
	sG8_T1 :[0..4] init 0;
	triesG8_T1 : [0..4] init 0;

	[success0_1] (bln & var1 >= 1.2) & sG8_T1 = 0 -> (sG8_T1'=1);//init to running
	[success0_1] !(bln & var1 >= 1.2) & sG8_T1 = 0 -> (sG8_T1'=3);//init to skipped




	[] sG8_T1 = 1 & triesG8_T1 < maxRetriesG8_T1 + 1 -> rTaskG8_T1 : (sG8_T1'=2) + (1 - rTaskG8_T1) : (triesG8_T1'=triesG8_T1+1);//try
	[] sG8_T1 = 1 & triesG8_T1 = maxRetriesG8_T1 + 1 -> (sG8_T1'=4);//no more retries
	[success0_2] sG8_T1 = 2 -> (sG8_T1'=2);//final state success
	[success0_2] sG8_T1 = 3 -> (sG8_T1'=3);//final state skipped
	[failG8_T1] sG8_T1 = 4 -> (sG8_T1'=4);//final state failure
endmodule

formula G8 = ((sG8_T1=2));

const double rTaskG9_T1_1=0.99;

module G9_T1_1_Testing
	sG9_T1_1 :[0..4] init 0;
	
	[success0_0] (varTesting & bln & var1 >= 1.2) & sG9_T1_1 = 0 -> (sG9_T1_1'=1);//init to running
	[success0_0] !(varTesting & bln & var1 >= 1.2) & sG9_T1_1 = 0 -> (sG9_T1_1'=3);//init to skipped




	[] sG9_T1_1 =  1 -> rTaskG9_T1_1 : (sG9_T1_1'=2) + (1 - rTaskG9_T1_1) : (sG9_T1_1'=4);//running to final state
	[success0_1] sG9_T1_1 = 2 -> (sG9_T1_1'=2);//final state success
	[success0_1] sG9_T1_1 = 3 -> (sG9_T1_1'=3);//final state skipped
	[failG9_T1_1] sG9_T1_1 = 4 -> (sG9_T1_1'=4);//final state failure
endmodule

const double rTaskG9_T1_2=0.99;

module G9_T1_2_Exception
	sG9_T1_2 :[0..4] init 0;
	
	[success0_0] (bln & var1 >= 1.2) & sG9_T1_2 = 0 -> (sG9_T1_2'=1);//init to running
	[success0_0] !(bln & var1 >= 1.2) & sG9_T1_2 = 0 -> (sG9_T1_2'=3);//init to skipped




	[] sG9_T1_2 =  1 -> rTaskG9_T1_2 : (sG9_T1_2'=2) + (1 - rTaskG9_T1_2) : (sG9_T1_2'=4);//running to final state
	[success1_1] sG9_T1_2 = 2 -> (sG9_T1_2'=2);//final state success
	[success1_1] sG9_T1_2 = 3 -> (sG9_T1_2'=3);//final state skipped
	[failG9_T1_2] sG9_T1_2 = 4 -> (sG9_T1_2'=4);//final state failure
endmodule

formula G9 = (((sG9_T1_1=2) | (sG9_T1_1=3 & (!(varTesting) | !(bln) | !(var1 >= 1.2)))) & ((sG9_T1_2=2) | (sG9_T1_2=3 & (!(bln) | !(var1 >= 1.2)))));

formula G3 = G8 & G9;

const double rTaskG4_T1=0.99;

module G4_T1_TrackLineLocation
	sG4_T1 :[0..4] init 0;
	
	[success0_0]  sG4_T1 = 0 -> (sG4_T1'=1);//init to running
	



	[] sG4_T1 =  1 -> rTaskG4_T1 : (sG4_T1'=2) + (1 - rTaskG4_T1) : (sG4_T1'=4);//running to final state
	[success2_1] sG4_T1 = 2 -> (sG4_T1'=2);//final state success
	[success2_1] sG4_T1 = 3 -> (sG4_T1'=3);//final state skipped
	[failG4_T1] sG4_T1 = 4 -> (sG4_T1'=4);//final state failure
endmodule

formula G4 = ((sG4_T1=2));

formula G1 = G3 & G4;
const bool bln;
const double var1;
const bool varTesting;


