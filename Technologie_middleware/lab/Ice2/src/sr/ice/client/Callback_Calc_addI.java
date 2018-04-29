package sr.ice.client;

import Demo.Callback_Calc_add;
import Ice.LocalException;

public class Callback_Calc_addI extends Callback_Calc_add
{

	@Override
	public void response(long __ret) 
	{
		System.out.println("RESULT (callback) = " + __ret);
	}

	@Override
	public void exception(LocalException __ex) 
	{
		System.out.println("EXCEPTION: " + __ex);
	}


}
