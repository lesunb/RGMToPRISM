dtmc

//formula noError = true & sG1_T1 < 4 & sG2_T2 < 4 & sG3_T3 < 4;


const double rTaskG1_T1=0.99;

module G1_T1_Plan1
	sG1_T1 :[0..4] init 0;
	
	[success0_0]  sG1_T1 = 0 -> (sG1_T1'=1);//init to running
	



	[] sG1_T1 =  1 & sG5_T5 = 2 -> rTaskG1_T1 : (sG1_T1'=2) + (1 - rTaskG1_T1) : (sG1_T1'=4);//running to final state
	[success0_1] sG1_T1 = 2 -> (sG1_T1'=2);//final state success
	[success0_1] sG1_T1 = 3 -> (sG1_T1'=3);//final state skipped
	[failG1_T1] sG1_T1 = 4 -> (sG1_T1'=4);//final state failure
endmodule

formula G1 = ((sG1_T1=2));

const double rTaskG2_T2=0.99;

module G2_T2_Plan2
	sG2_T2 :[0..4] init 0;
	
	[success0_1] (G1) &  sG2_T2 = 0 -> (sG2_T2'=1);//init to running
	



	[] sG2_T2 =  1 -> rTaskG2_T2 : (sG2_T2'=2) + (1 - rTaskG2_T2) : (sG2_T2'=4);//running to final state
	[success0_2] sG2_T2 = 2 -> (sG2_T2'=2);//final state success
	[success0_2] sG2_T2 = 3 -> (sG2_T2'=3);//final state skipped
	[failG2_T2] sG2_T2 = 4 -> (sG2_T2'=4);//final state failure
endmodule

formula G2 = ((sG2_T2=2));

const double rTaskG3_T3=0.99;

module G3_T3_Plan4
	sG3_T3 :[0..4] init 0;
	
	[success0_2] (G2) &  sG3_T3 = 0 -> (sG3_T3'=1);//init to running
	



	[] sG3_T3 =  1 -> rTaskG3_T3 : (sG3_T3'=2) + (1 - rTaskG3_T3) : (sG3_T3'=4);//running to final state
	[success0_3] sG3_T3 = 2 -> (sG3_T3'=2);//final state success
	[success0_3] sG3_T3 = 3 -> (sG3_T3'=3);//final state skipped
	[failG3_T3] sG3_T3 = 4 -> (sG3_T3'=4);//final state failure
endmodule

//formula noError = true & sG5_T5 < 4 & sG6_T6 < 4;


const double rTaskG5_T5=0.99;

module G5_T5_Plan5
	sG5_T5 :[0..4] init 0;
	
	[success0_0]  sG5_T5 = 0 -> (sG5_T5'=1);//init to running
	



	[] sG5_T5 =  1 -> rTaskG5_T5 : (sG5_T5'=2) + (1 - rTaskG5_T5) : (sG5_T5'=4);//running to final state
	[success0_1] sG5_T5 = 2 -> (sG5_T5'=2);//final state success
	[success0_1] sG5_T5 = 3 -> (sG5_T5'=3);//final state skipped
	[failG5_T5] sG5_T5 = 4 -> (sG5_T5'=4);//final state failure
endmodule

formula G5 = ((sG5_T5=2));

const double rTaskG6_T6=0.99;

module G6_T6_Plan3
	sG6_T6 :[0..4] init 0;
	
	[success0_0]  sG6_T6 = 0 -> (sG6_T6'=1);//init to running
	



	[] sG6_T6 =  1 & sG3_T3 = 2 -> rTaskG6_T6 : (sG6_T6'=2) + (1 - rTaskG6_T6) : (sG6_T6'=4);//running to final state
	[success1_1] sG6_T6 = 2 -> (sG6_T6'=2);//final state success
	[success1_1] sG6_T6 = 3 -> (sG6_T6'=3);//final state skipped
	[failG6_T6] sG6_T6 = 4 -> (sG6_T6'=4);//final state failure
endmodule

formula G6 = ((sG6_T6=2));

formula G4 = G5 & G6 & G2;

formula G3 = ((sG3_T3=2));

formula G0 = G1 | G2 | G3;


