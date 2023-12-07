import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

//Project by Laila Hamdy - DS

public class ProjectApplet extends Applet {

    //Tool Buttons
	Button recButton = new Button("Rectangle");
    Button ovalButton = new Button("Oval");
    Button lineButton = new Button("Line");
    Button freeButton = new Button("Free Hand");
    Button eraseButton = new Button("Eraser");
    Button clearButton = new Button("Clear All");
	
	//Color Buttons
    Button rButton = new Button("Red");
    Button gButton = new Button("Green");
    Button bluButton = new Button("Blue");
    Button blaButton = new Button("Black");

	//Fill
    Checkbox fillCheck = new Checkbox("Filled");
	
	//Variables
	private int currentX1;
    private int currentY1;
	private int currentX2;
	private int currentY2;
	private boolean dragged = false;
    private static int currentMode;
    private static Color currentColor;
    private static boolean currentState;
	private static int eraserOffset=3;
	private ArrayList<Shape> myShapes = new ArrayList<Shape>();
    public static final int RECT = 1;
    public static final int OVAL = 2;
    public static final int LINE = 3;
    public static final int FREE = 4;
    public static final int ERASE = 5;

	
	//Methods
	public void setCurrentMode(int mode) {
        switch (mode) {
            case RECT:
            case OVAL:
            case LINE:
            case FREE:
            case ERASE:
                currentMode = mode;
			
        }
    }
	public static Color getCurrentColor(){
		return currentColor;
	}
	public void setCurrentColor(Color color) {
		currentColor = color;
    }
	public static int getCurrentMode(){
		return currentMode;
	}	
	public static boolean getCurrentState(){
		return currentState;
	}
	public void setCurrentState(boolean s) {
		currentState = s;
    }
	
	public void init(){
		//Initalize mode and color
		setCurrentMode(RECT);
		setCurrentColor(Color.BLACK);
		
		//Adding the structure
        add(recButton);
        add(ovalButton);
        add(lineButton);
        add(freeButton);
        add(eraseButton);
        add(clearButton);
        add(blaButton);
        add(rButton);
        add(gButton);
        add(bluButton);
        add(fillCheck);
		
		//Adding listeners
		recButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                setCurrentMode(RECT);
            }
        });
		ovalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                setCurrentMode(OVAL);
            }
        });
        lineButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                setCurrentMode(LINE);
            }
        });
		freeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                setCurrentMode(FREE);
            }
        });
		eraseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                setCurrentMode(ERASE);
            }
        });
		clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                myShapes.clear();
				repaint();
            }
        });
		rButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                setCurrentColor(Color.RED);
            }
        });
		blaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                setCurrentColor(Color.BLACK);
            }
        });
		bluButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                setCurrentColor(Color.BLUE);
            }
        });
		gButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                setCurrentColor(Color.GREEN);
            }
        });
		fillCheck.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e){
                setCurrentState(fillCheck.getState());
            }
        });
		
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				currentX1 = e.getX();
				currentY1 = e.getY();
				currentX2 = e.getX();
				currentY2 = e.getY();
				repaint();
			}
			public void mouseReleased(MouseEvent e) {
				switch(currentMode){
					case RECT:
					if(dragged){
						Rect rec = new Rect();
						rec.setValues(currentX1,currentY1,currentX2,currentY2);
						myShapes.add(rec);
						rec.setShapeColor(getCurrentColor());
						rec.setFillState(getCurrentState());
					}
					dragged = false;
					//repaint();
					break;
					case OVAL:
					if(dragged){
						Oval oval = new Oval();
						oval.setValues(currentX1,currentY1,currentX2,currentY2);
						myShapes.add(oval);
						oval.setShapeColor(getCurrentColor());
						oval.setFillState(getCurrentState());
					}
					dragged = false;
					//repaint();
					break;
				case LINE:
					if(dragged){
						Line l = new Line();
						l.setValues(currentX1,currentY1,currentX2,currentY2);
						myShapes.add(l);
						l.setShapeColor(getCurrentColor());
						l.setFillState(getCurrentState());
					}
					dragged = false;
					//repaint();
					break;
				case FREE:
					if(dragged){
						Line l = new Line();
						l.setValues(currentX1,currentY1,currentX2,currentY2);
						currentX1 = currentX2;
						currentY1 = currentY2;
						myShapes.add(l);
						l.setShapeColor(getCurrentColor());
						l.setFillState(getCurrentState());
					}
					dragged = false;
					//repaint();
					break;
				case ERASE:
					if(dragged){
						Rect rec = new Rect();
						rec.setValues(currentX1,currentY1,currentX2,currentY2);
						myShapes.add(rec);
						currentX1 = currentX2;
						currentY1 = currentY2;
						rec.setShapeColor(Color.WHITE);
						rec.setFillState(true);
					}
					dragged = false;
					break;
			}
		}
		});
		this.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
			currentX2 = e.getX();
			currentY2 = e.getY();
			if (currentMode == FREE){
				Line l = new Line();
				l.setValues(currentX1,currentY1,currentX2,currentY2);
				currentX1 = currentX2;
				currentY1 = currentY2;
				myShapes.add(l);
				l.setShapeColor(getCurrentColor());
				l.setFillState(getCurrentState());
			}
			dragged = true;
			repaint();
			if (currentMode == ERASE){
				Rect rec = new Rect();
				rec.setValues( Math.min(currentX1, currentX2) - eraserOffset, Math.min(currentY1, currentY2) - eraserOffset,
							   Math.max(currentX1, currentX2) + eraserOffset, Math.max(currentY1, currentY2) + eraserOffset);
				myShapes.add(rec);
				currentX1 = currentX2;
				currentY1 = currentY2;
				rec.setShapeColor(Color.WHITE);
				rec.setFillState(true);
			}
			dragged = true;
			repaint();
			}});
	}
	public void paint(Graphics g) {
		switch(currentMode){
            case RECT:
				for (int i=0; i<myShapes.size(); i++){
					myShapes.get(i).draw(g);
					}
				if (dragged) {
					g.setColor(getCurrentColor());
					if(currentState ==false){
						g.drawRect(Math.min(currentX1,currentX2), Math.min(currentY1,currentY2), Math.abs(currentX2-currentX1), Math.abs(currentY2-currentY1));
					}
					else {
						g.fillRect(Math.min(currentX1,currentX2), Math.min(currentY1,currentY2), Math.abs(currentX2-currentX1), Math.abs(currentY2-currentY1));

					}
				}
			break;
            case OVAL:
				for (int i=0; i<myShapes.size(); i++){
					myShapes.get(i).draw(g);
					}
				if (dragged) {
					g.setColor(getCurrentColor());
					if(currentState ==false){
						g.drawOval(Math.min(currentX1,currentX2), Math.min(currentY1,currentY2), Math.abs(currentX2-currentX1), Math.abs(currentY2-currentY1));
					}
					else {
						g.fillOval(Math.min(currentX1,currentX2), Math.min(currentY1,currentY2), Math.abs(currentX2-currentX1), Math.abs(currentY2-currentY1));

					}
				}
			break;
            case LINE:
			for (int i=0; i<myShapes.size(); i++){
					myShapes.get(i).draw(g);
					}
				if (dragged) {
					g.setColor(getCurrentColor());
					g.drawLine(currentX1, currentY1, currentX2, currentY2);
					}
			break;
            case FREE:
			for (int i=0; i<myShapes.size(); i++){
					myShapes.get(i).draw(g);
					}
				if (dragged) {
					g.setColor(getCurrentColor());
					g.drawLine(currentX1, currentY1, currentX2, currentY2);
					}
			break;
            case ERASE:
			for (int i=0; i<myShapes.size(); i++){
					myShapes.get(i).draw(g);
					}
				if (dragged) {
					g.setColor(Color.WHITE);
					g.fillRect(Math.min(currentX1-eraserOffset,currentX2-eraserOffset), Math.min(currentY1-eraserOffset,currentY2-eraserOffset), Math.abs(currentX2+eraserOffset - currentX1+eraserOffset), Math.abs(currentY2+eraserOffset - currentY1+eraserOffset));
					}
			break;
		}
	}
}

abstract class Shape {
	//Attributes
	protected boolean fillState;
	protected Color color;
	protected int val1;
	protected int val2;
	protected int val3;
	protected int val4;

	
	//Setters and getters 
	public void setFillState (boolean s) {
		fillState = s;
	}
	public boolean getFillState () {
		return fillState;
	}
	public void setShapeColor (Color c) {
		color = c;
	}
	public Color getShapeColor () {
		return color;
	}
	public void setValues (int no1, int no2, int no3, int no4){
		val1 = no1;
		val2 = no2;
		val3 = no3;
		val4 = no4;
	}
	
	//draw function to override
	public abstract void draw(Graphics g);
}

class Line extends Shape {
	public void draw(Graphics g) {
		g.setColor(getShapeColor());
		g.drawLine(val1,val2,val3,val4);
	}
}
class Rect extends Shape {
	public void draw(Graphics g) {
		g.setColor(getShapeColor());
		if (this.fillState){
		g.fillRect(Math.min(val1,val3),Math.min(val2,val4),Math.abs(val1-val3), Math.abs(val2-val4));
		}
		if (!this.fillState){
		g.drawRect(Math.min(val1,val3),Math.min(val2,val4),Math.abs(val1-val3), Math.abs(val2-val4));
		}
	}
}
class Oval extends Shape {	
	public void draw(Graphics g) {
		g.setColor(getShapeColor());
		if (this.fillState){
		g.fillOval(Math.min(val1,val3),Math.min(val2,val4),Math.abs(val1-val3), Math.abs(val2-val4));
		}
		if (!this.fillState){
		g.drawOval(Math.min(val1,val3),Math.min(val2,val4),Math.abs(val1-val3), Math.abs(val2-val4));
		}
	}
}