package com.nantian.npbs.security;

import java.util.* ;

public class SuiJiShu{

	public static void main(String args[]){     
		SuiJiShu sjs=new SuiJiShu();
		System.out.println(sjs.getrannumber());
	} 

	public String getrannumber(){
		StringBuffer strbufguess=new StringBuffer();
		String strguess=null;
		int []nums={0,1,2,3,4,5,6,7,8,9};
		Random rannum=new Random();
		int count;
		int i=0,temp_i=0;
		for (int j=10;j>4;j--){
			i=0;temp_i=0;
			count=rannum.nextInt(j);
			while(i<=count){
				if (nums[temp_i]==-1) temp_i++;
				else {
					i++;temp_i++;
				}  
			}
			strbufguess.append(Integer.toString(nums[temp_i-1]));    
			nums[temp_i-1]=-1;
		}  
		strguess=strbufguess.toString();
		rannum=null;
		strbufguess=null;
		nums=null;
		return strguess;
	}
	
	public static Byte[] getRandombyte() {
		Random random = new Random();
		Byte[] b= {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
		for (int i = 0; i < 16; i++) {
			Integer is =  random.nextInt(16);
			b[i]=Byte.parseByte(is.toString());
			
		} 
		return b;
	}

}
