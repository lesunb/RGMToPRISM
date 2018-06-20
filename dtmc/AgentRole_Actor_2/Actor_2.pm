dtmc

formula noError = true & sG5_T5 < 4 & sG6_T6 < 4;


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
	



	[] sG6_T6 =  1 -> rTaskG6_T6 : (sG6_T6'=2) + (1 - rTaskG6_T6) : (sG6_T6'=4);//running to final state
	[success1_1] sG6_T6 = 2 -> (sG6_T6'=2);//final state success
	[success1_1] sG6_T6 = 3 -> (sG6_T6'=3);//final state skipped
	[failG6_T6] sG6_T6 = 4 -> (sG6_T6'=4);//final state failure
endmodule

formula G6 = ((sG6_T6=2));

formula G4 = G5 & G6;


