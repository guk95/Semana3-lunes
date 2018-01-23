/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql4java;

/**
 *
 * @author Telematica-2-0
 */
public class funciones 
{
    public boolean isNumeric(Object element) 
    {
        try 
        {
            for (int i = 0; i < element.toString().length(); i++)
                Integer.parseInt(String.valueOf(element.toString().charAt(i)));
        }
        catch(NumberFormatException ex)
        {
            return false;
        }
        return true;
    }
}
