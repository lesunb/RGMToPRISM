dtmc

formula noError = true & sG3_T1 < 4 & (sG4_T2 < 4 | (true  & sG4_T4 < 4)) & sG5_T3 < 4;


const double rTaskG3_T1=0.99;

module G3_T1_Task1
	sG3_T1 :[0..4] init 0;
	
	[success0_0]  sG3_T1 = 0 -> (sG3_T1'=1);//init to running
	



	[] sG3_T1 =  1 -> rTaskG3_T1 : (sG3_T1'=2) + (1 - rTaskG3_T1) : (sG3_T1'=4);//running to final state
	[success0_1] sG3_T1 = 2 -> (sG3_T1'=2);//final state success
	[success0_1] sG3_T1 = 3 -> (sG3_T1'=3);//final state skipped
	[failG3_T1] sG3_T1 = 4 -> (sG3_T1'=4);//final state failure
endmodule

formula G3 = ((sG3_T1=2));

formula G1 = G3;

const double rTaskG4_T2=0.99;

module G4_T2_Task2
	sG4_T2 :[0..4] init 0;
	
	[success0_0]  sG4_T2 = 0 -> (sG4_T2'=1);//init to running
	

	[] sG4_T2 =  1 -> rTaskG4_T2 : (sG4_T2'=2) + (1 - rTaskG4_T2) : (sG4_T2'=4);//running to final state
	[success1_1] sG4_T2 = 2 -> (sG4_T2'=2);//final state success
	[success1_1] sG4_T2 = 3 -> (sG4_T2'=3);//final state skipped
	[failG4_T2] sG4_T2 = 4 -> (sG4_T2'=4);//final state failure
endmodule

const double rTaskG4_T4=0.99;

module G4_T4_Task4
	sG4_T4 :[0..4] init 0;
	
	[failG4_T2]   sG4_T4 = 0 -> (sG4_T4'=1);//init to running
	[success1_1]  sG4_T4 = 0 -> (sG4_T4'=3);//not used, skip running

	[] sG4_T4 =  1 -> rTaskG4_T4 : (sG4_T4'=2) + (1 - rTaskG4_T4) : (sG4_T4'=4);//running to final state
	[success1_2] sG4_T4 = 2 -> (sG4_T4'=2);//final state success
	[success1_2] sG4_T4 = 3 -> (sG4_T4'=3);//final state skipped
	[failG4_T4] sG4_T4 = 4 -> (sG4_T4'=4);//final state failure
endmodule

formula G4 = ((((sG4_T2=2 & true) | (sG4_T2=4 & sG4_T4=2))));

const double rTaskG5_T3=0.99;

module G5_T3_Task3
	sG5_T3 :[0..4] init 0;
	
	[success1_2] (G4) &  sG5_T3 = 0 -> (sG5_T3'=1);//init to running
	



	[] sG5_T3 =  1 -> rTaskG5_T3 : (sG5_T3'=2) + (1 - rTaskG5_T3) : (sG5_T3'=4);//running to final state
	[success1_3] sG5_T3 = 2 -> (sG5_T3'=2);//final state success
	[success1_3] sG5_T3 = 3 -> (sG5_T3'=3);//final state skipped
	[failG5_T3] sG5_T3 = 4 -> (sG5_T3'=4);//final state failure
endmodule

formula G5 = ((sG5_T3=2));

formula G2 = G4 & G5;

formula G0 = G1 | G2;


