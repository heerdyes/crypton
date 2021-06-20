package system;

import javax.swing.JTextPane;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.text.Document;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.event.UndoableEditEvent;
//import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.AttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.text.AbstractDocument;
//import javax.swing.text.Caret;
import javax.swing.text.Element;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Point;

import java.awt.print.PrinterException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

public class TextBean extends JPanel implements DocumentListener, UndoableEditListener, ActionListener, CaretListener {
    private int curpos;
    private JScrollPane sp;
    private JTextPane tp;
    private JTextField tf;
    private StyledDocument sdoc;
    private AbstractDocument doc;
    private TextBeanListener tblis;
    private SimpleAttributeSet attset;
    //private String fontStyle;
    //private int fontSize;
    
    public TextBean(int r, int c) {
        setLayout(new BorderLayout());
        
        tp = new JTextPane();
        tp.setPreferredSize(new Dimension((Toolkit.getDefaultToolkit().getScreenSize().width / 3), (Toolkit.getDefaultToolkit().getScreenSize().height / 3)));
        tp.addCaretListener(this);

        doc = (AbstractDocument)tp.getStyledDocument();
        doc.addDocumentListener(this);
        doc.addUndoableEditListener(this);
        
        sp = new JScrollPane(tp);
        
        tf = new JTextField();
        tf.setEditable(false);
        
        add(sp, "Center");
        add(tf, "South");
        
        validate();
        tp.requestFocus();
    }
    
    // initializing the text area ... 
    public void initTextPane() {
        //attset = new SimpleAttributeSet();
        
        tp.setText("");
        validate();
        tp.requestFocus();
    }
    
    // loading text area ... 
    public void loadText(String sd) {
        //tp.append(sd);
        try {
            doc.insertString(0, sd, null);
        }
        catch(Exception exc) {
            System.out.println("system.TextBean: public void loadText(String) ...\nexception: "+ exc);
        }
        
        validate();
        tp.requestFocus();
    }
    
    // get the contents of the document ... 
    public String getDocumentContent() throws BadLocationException {
        return doc.getText(0, doc.getLength() - 1);
    }
    
    // updating the status bar ... 
    private void updateCaretPosition() {
        int carpos = tp.getCaretPosition();
        Point cp = tp.getCaret().getMagicCaretPosition();
        tf.setText("caret: ["+ cp.x +", "+ cp.y +"] :: caret pos: "+ carpos);
        validate();
    }
    
    private void updateCaretDot(int cd) {
        int cardot = cd;
        //Point cp = tp.getCaret().getMagicCaretPosition();
        //tf.setText("caret: ["+ cp.x +", "+ cp.y +"] :: caret pos: "+ cardot);
        tf.setText("caret: ["+ cardot +"]");
        validate();
    }
    
    // changing colors ... 
    public void setBackColor(Color c) {
        tp.setBackground(c);
        validate();
        tp.requestFocus();
    }
    
    public void setForeColor(Color c) {
        tp.setForeground(c);
        validate();
        tp.requestFocus();
    }
    
    // doing print ...
    public void doPrint() {
        try {
            boolean complete = tp.print();
            if(complete) {
                JOptionPane.showMessageDialog(null, "Printed Successfully!", "", JOptionPane.OK_CANCEL_OPTION);
            }
            else {
                JOptionPane.showMessageDialog(null, "problem encountered while printing!", "", JOptionPane.OK_CANCEL_OPTION);
            }
        }
        catch(PrinterException pe) {
            JOptionPane.showMessageDialog(null, "Printer Exception", "Check your printer", JOptionPane.OK_CANCEL_OPTION);
        }
    }
    
    // doing cut, copy, and paste ...
    public void doCut() {
        System.out.println("docut");
        if(tp.getSelectedText() != null) {
            tp.cut();
            tp.requestFocus();
        }
    }
    
    public void doCopy() {
        System.out.println("docopy");
        if(tp.getSelectedText() != null) {
            tp.copy();
            tp.requestFocus();
        }
    }
    
    public void doPaste() {
        System.out.println("dopaste");
        tp.paste();
        tp.requestFocus();
    }
    
    // another subroutine ...
    
    protected void showText(String txt, AttributeSet attset) {
        Document doc = tp.getDocument();
        try {
            doc.insertString(doc.getLength(), txt, attset);
        }
        catch(BadLocationException ble) { ble.printStackTrace(); }
    }
    
    // important
    
    private SimpleAttributeSet getSetAttributes(boolean isBold, boolean isItalic, boolean isUnderline) {
        attset = new SimpleAttributeSet();

        StyleConstants.setItalic(attset, isItalic);
        StyleConstants.setBold(attset, isBold);
        StyleConstants.setUnderline(attset, isUnderline);
        
        // relatively recent changes ... 
        
        /* required data ::
            boolean { isFontFamily, isFontSize, isBackground, isForeground };
            Color   { backgroundColor, foregroundColor };
            String  { fontFamilyName };
            int     { fontSize };
        */
        
        // keep this commented until complete impact is analyzed and fixed.
        
        /*
        if(isFontFamily) { StyleConstants.setFontFamily(attset, fontFamilyName); }
        if(isFontSize)   { StyleConstants.setFontSize(attset, fontSize); }
        if(isBackground) { StyleConstants.setBackground(attset, backgroundColor); }
        if(isForeground) { StyleConstants.setForeground(attset, foregroundColor); }
        */

        return attset;
    }
    
    public void updateStyle(boolean b, boolean i, boolean u) {
        if(tp.getSelectedText() == null) {
            tp.setCharacterAttributes(getSetAttributes(b, i, u), true);
        }
        else {
            StyledDocument doc = tp.getStyledDocument();
            int start = tp.getSelectionStart();
            int length = tp.getSelectionEnd() - start;
            doc.setCharacterAttributes(start, length, getSetAttributes(b, i, u), false);
        }
    }
    
    public void updateStyle(String fontStyle, int fontSize) {
        if(tp.getSelectedText() == null) {
            tp.setCharacterAttributes(getSetAttributes(fontStyle, fontSize), true);
        }
        else {
            StyledDocument doc = tp.getStyledDocument();
            int start = tp.getSelectionStart();
            int length = tp.getSelectionEnd() - start;
            doc.setCharacterAttributes(start, length, getSetAttributes(fontStyle, fontSize), false);
        }
    }
    
    private SimpleAttributeSet getSetAttributes(String ft, int fs) {
        attset = new SimpleAttributeSet();
        
        StyleConstants.setFontFamily(attset, ft);
        StyleConstants.setFontSize(attset, fs);
        
        return attset;
    }
    
    public void updateStyle(Color fc, boolean isF) {
        if(tp.getSelectedText() == null) {
            tp.setCharacterAttributes(getSetAttributes(fc, isF), true);
        }
        else {
            StyledDocument doc = tp.getStyledDocument();
            int start = tp.getSelectionStart();
            int length = tp.getSelectionEnd() - start;
            doc.setCharacterAttributes(start, length, getSetAttributes(fc, isF), false);
        }
    }
    
    private SimpleAttributeSet getSetAttributes(Color c, boolean isForeground) {
        attset = new SimpleAttributeSet();
        
        if(isForeground) {
            //StyleConstants.setBackground(attset, StyleConstants.getBackground(attset));
            StyleConstants.setForeground(attset, c);
        }
        else {
            //StyleConstants.setForeground(attset, StyleConstants.getForeground(attset));
            StyleConstants.setBackground(attset, c);
        }
        
        return attset;
    }
    
    public void doDump() {
        doc = (AbstractDocument)tp.getDocument();
        doc.dump(System.out);
    }
    
    public void loadFile(File filename) {
        try {
            int intch;
            char ch;

            attset = new SimpleAttributeSet();
            FileReader reader = new FileReader(filename);

            boolean readingTag = false;
            boolean readingValue = false;

            StringBuffer tag = new StringBuffer();
            StringBuffer element = new StringBuffer();
            StringBuffer value = new StringBuffer();
            
            sdoc = tp.getStyledDocument();

            while((intch = reader.read()) != -1) {
                ch = (char)intch;
                if(ch == '<') {
                    if(element.length() > 0) {
                        sdoc.insertString(tp.getDocument().getLength(), element.toString(), attset);
                        element = new StringBuffer();
                    }

                    readingTag = true;
                    tag = new StringBuffer();
                    value = new StringBuffer();
                }
                else if(readingTag && ch == '>') {
                    String tagString = tag.toString();
                    String valueString = value.toString();

                    if(tagString.equals("Start Bold")) {
                        StyleConstants.setBold(attset, true);
                    }
                    else if(tagString.equals("End Bold")) {
                        StyleConstants.setBold(attset, false);
                    }
                    else if(tagString.equals("Start Italic")) {
                        StyleConstants.setItalic(attset, true);
                    }
                    else if(tagString.equals("End Italic")) {
                        StyleConstants.setItalic(attset, false);
                    }
                    else if(tagString.equals("Start Underline")) {
                        StyleConstants.setUnderline(attset, true);
                    }
                    else if(tagString.equals("End Underline")) {
                        StyleConstants.setUnderline(attset, false);
                    }
                    else if(tagString.equals("Font Name")) {
                        StyleConstants.setFontFamily(attset, valueString);
                    }
                    else if(tagString.equals("Font Size")) {
                        StyleConstants.setFontSize(attset, Integer.parseInt(valueString));
                    }

                    readingTag = false;
                    readingValue = false;
                }
                else if(readingTag && ch == '=') {
                    readingValue = true;
                }
                else if(readingTag) {
                    if(readingValue) {
                        value = value.append(ch);
                    }
                    else {
                        tag = tag.append(ch);
                    }
                }
                else {
                    element = element.append(ch);
                }
            }

            if(element.length() > 0) {
                sdoc.insertString(sdoc.getLength(), element.toString(), attset);
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("Can't find: "+ filename);
        }
        catch(IOException e) {
            System.out.println("IO Exception with Reader");
        }
        catch(BadLocationException e) {
            System.out.println("Bad Location on open");
        }
    }
    
    public void saveContents(File filename) {
        Element els = tp.getDocument().getDefaultRootElement();

        try {
            FileWriter writer = new FileWriter(filename);
            saveElement(els, attset, writer);
            writer.close();
        }
        catch(IOException e) {
            System.out.println("Can't open: "+ filename);
        }
    }
    
    private AttributeSet saveElement(Element el, AttributeSet attribs, FileWriter writer) throws IOException {
        AttributeSet current;

        if(el.isLeaf()) {
            writer.write(reconcileAttributes(attribs, el.getAttributes()));

            int start = el.getStartOffset();
            int end = el.getEndOffset();

            try {
                writer.write(el.getDocument().getText(start, end - start));
            }
            catch(BadLocationException e) {
                System.out.println("Bad Location: "+ start +"|"+ end);
            }

            current = el.getAttributes();
        }
        else {
            current = attribs;

            for(int i = 0; i < el.getElementCount(); i++) {
                current = saveElement(el.getElement(i), current, writer);
            }
        }

        return current;
    }
    
    private String reconcileAttributes(AttributeSet startSet, AttributeSet thisSet) {
        String bold = getToggleString(StyleConstants.isBold(startSet), StyleConstants.isBold(thisSet), "<Start Bold>", "<End Bold>");

        String italic = getToggleString(StyleConstants.isItalic(startSet), StyleConstants.isItalic(thisSet), "<Start Italic>", "<End Italic>");

        String underline = getToggleString(StyleConstants.isUnderline(startSet), StyleConstants.isUnderline(thisSet), "<Start Underline>", "<End Underline>");

        String fontName = "";
        if(StyleConstants.getFontFamily(startSet) != StyleConstants.getFontFamily(thisSet)) {
            fontName = "<Font Name="+ StyleConstants.getFontFamily(thisSet) +">";
        }

        String fontSize = "";
        if(StyleConstants.getFontSize(startSet) != StyleConstants.getFontSize(thisSet)) {
            fontSize = "<Font Size="+ StyleConstants.getFontSize(thisSet) +">";
        }

        return bold + italic + underline + fontName + fontSize;
    }
    
    private String getToggleString(boolean state1, boolean state2, String on, String off) {
        if(state1 != state2) {
            if(state1) {
                return off;
            }
            return on;
        }

        return "";
    }
    
    /*...   the plugpoints   ...*/
    
    public void addTextBeanListener(TextBeanListener tbl) {
        tblis = tbl;
    }
    
    public void fireTextBeanEvent(TextBeanEvent tbe) {
        tblis.textBeanActionPerformed(tbe);
    }
    
    /*...   the listener stuff   ... */
    
    // actionlistener implementation ...
    
    public void actionPerformed(ActionEvent ae) {
        TextBeanEvent tbe = new TextBeanEvent(ae, ae.getSource().toString());
        fireTextBeanEvent(tbe);
    }
    
    // documentlistener implementation ... 
    
    public void insertUpdate(DocumentEvent de) {
        //updateStptus();
        TextBeanEvent tbe = new TextBeanEvent(de, "offset = "+ de.getOffset());
        fireTextBeanEvent(tbe);
    }
    
    public void removeUpdate(DocumentEvent de) {
        //updateStptus();
        TextBeanEvent tbe = new TextBeanEvent(de, "offset = "+ de.getOffset());
        fireTextBeanEvent(tbe);
    }
    
    public void changedUpdate(DocumentEvent de) {
        //updateStptus();
    }
    
    // the caret listener implementation ...
    
    public void caretUpdate(CaretEvent ce) {
        updateCaretDot(ce.getDot());
    }
    
    // undoableeditlistener implementation
    
    public void undoableEditHappened(UndoableEditEvent ude) {
        TextBeanEvent tbe = new TextBeanEvent(ude, ude.getSource().toString());
        fireTextBeanEvent(tbe);
    }
}
