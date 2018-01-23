/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mysql4java;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Lic. Jorge Isaac Vásquez Valenciano
 */
public class NanosoftLayOut {

    private final int jframe_width, jframe_height, object_margin, objectMarginTop;
    private int components_height, components_width, tmpWidth, tmpHeight;
    private boolean isRow, haveMarginTop, firstObjectinMarginTop;

    private int totalWidth, currentRow;

    /**
     * NanosofotLayOut: By Lic. Isaac Vásquez
     *
     * @param w JFrame (Main) Width
     * @param h JFrame (Main) Height
     * @param margin Elements Margin (top-bottom) between them.
     */
    public NanosoftLayOut(int w, int h, int margin) 
    {
        this.jframe_height = h;
        this.jframe_width = w;
        this.object_margin = margin;
        this.isRow = false;
        this.totalWidth = 0;
        this.currentRow = 0;
        this.objectMarginTop = 0;
        this.haveMarginTop = false;
        this.firstObjectinMarginTop = false;
    }
    
     /**
     * NanosofotLayOut: By Lic. Isaac Vásquez
     *
     * @param w JFrame (Main) Width
     * @param h JFrame (Main) Height
     * @param margin Elements Margin (top-bottom) between them.
     * @param InitMarginTop deja un espacio entre el JFrame y el primer componente.
     */
    public NanosoftLayOut(int w, int h, int margin, int InitMarginTop) 
    {
        this.jframe_height = h;
        this.jframe_width = w;
        this.object_margin = margin;
        this.isRow = false;
        this.totalWidth = 0;
        this.currentRow = 0;
        this.objectMarginTop = InitMarginTop;
        this.haveMarginTop = true;
        this.firstObjectinMarginTop = true;
    }

    /**
     * Retorna los valores inicializados en el constructor.
     *
     * @return
     */
    public Dimension getJFrameDimension() {
        return new Dimension(this.jframe_width, this.jframe_height);
    }

    public LayoutManager getNanosoftLayOut() {
        return null;
    }

    /**
     * Recibe el ancho del elemento para posicionarlo con el ancho del jframe
     *
     * @param element_width
     * @return el centro del JFrame
     */
    public int getXCenter(int element_width) 
    {
        if (this.isRow) 
        {
            if (this.currentRow == 1) {
                this.components_width = ((this.jframe_width / 2) - (this.totalWidth / 2));
                this.tmpWidth = element_width;
            } else {
                this.components_width += this.tmpWidth;// + this.object_margin;
                this.tmpWidth = element_width;
            }
            return this.components_width;
        } else {
            return (this.jframe_width / 2) - element_width / 2;
        }
    }

    /**
     * Recibe el ancho del elemento, calcula la longitud siguiente en Y del
     * JFrame
     *
     * @param element_height
     * @return
     */
    public int getYCenter(int element_height) 
    {
        if (this.firstObjectinMarginTop && this.haveMarginTop)
        {
            this.firstObjectinMarginTop = false;
            this.components_height += this.objectMarginTop;
        }
        if ((this.getRowState() && this.currentRow == 1) || !this.getRowState()) 
        {
            this.components_height += element_height + this.object_margin;
        }

        // ya que hace una suma antes de retornar el cálculo, debemos arreglarlo.
        return this.components_height - element_height;
    }

    /**
     * Recibe el ancho y el largo del elemento y lo posiciona automáticamente
     *
     * @param width El ancho del elemento (width)
     * @param height El largo del elemento (height)
     * @return
     */
    public Rectangle getRectangle(int width, int height) 
    {
        if (this.getRowState()) {
            throw new ClassFormatError("getRectangle(): You can´t cast this method inside a Row");
        }
        return new Rectangle(this.getXCenter(width), this.getYCenter(height), width, height);
    }

    public boolean getRowState() {
        return this.isRow;
    }

    public void setRow(Object[][] obj) {
        this.isRow = true;
        for (Object[] obj1 : obj) {
            this.totalWidth += (int) obj1[1];
        }
        if (this.totalWidth > this.jframe_width )
            throw new ClassFormatError("setRow(): elements width is bigger than main component.");
        
        Component comp = null;

        for (Object[] obj1 : obj) {
            this.currentRow++;
            comp = (Component) obj1[0];
            comp.setBounds(
                    this.getXCenter((int) obj1[1]),
                    this.getYCenter((int) obj1[2]),
                    (int) obj1[1],
                    (int) obj1[2]
            );
            try {
                if (comp instanceof JLabel) 
                {
                    JLabel jlbl = (JLabel) comp;
                    jlbl.setText(obj1[3].toString());
                }
                else if (comp instanceof JButton)
                {
                    JButton jbtn = (JButton) comp;
                    jbtn.setText(obj1[3].toString());                    
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
            }
            this.tmpHeight = (int) obj1[2];
        }

        this.isRow = false;
        this.totalWidth = 0;
        this.tmpWidth = 0;
        //this.components_height += this.tmpHeight + this.object_margin;
        this.tmpHeight = 0;
        this.currentRow = 0;
        this.tmpHeight = 0;

        //comp.getParent().add(comp);
    }
}
