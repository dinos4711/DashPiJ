/*
 * $Id$
 *
 * Copyright © 2000, 2015, Oracle and/or its affiliates. All rights reserved.
 *
 */

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class MyTest extends JFrame {

  private static final long serialVersionUID = -2130314832602029110L;

  class ClockPanel extends JPanel {

    private static final long serialVersionUID = 4723249997745282433L;

    public ClockPanel() {
      Timer timer = new Timer();
      timer.schedule(new TimerTask() {
        @Override
        public void run() {
          ClockPanel.this.repaint();
        }
      }, 0, 50);
    }

    @Override
    protected void paintComponent(Graphics g) {
      Graphics2D g2 = (Graphics2D)g;
      RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      rh.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
      g2.setRenderingHints(rh);
      g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

      int width = getWidth();
      int height = getHeight();

      double clockSize = Math.min(width, height) - 10;



      g.setColor(Color.BLACK);
      g.fillRect(0, 0, width, height);
      g.setColor(Color.WHITE);

      double xCenter = width / 2;
      double yCenter = height / 2;

      for (int i = 0; i < 60; i++) {
        double angleP = -90 + i * 6;
        double anglePInRadians = Math.toRadians(angleP);
        double xP = xCenter + (clockSize / 2) * Math.cos(anglePInRadians);
        double yP = yCenter + (clockSize / 2) * Math.sin(anglePInRadians);
        if (i % 5 == 0) {
          g2.draw(new Ellipse2D.Double(xP - 1, yP - 1, 3, 3));
        } else {
          g2.draw(new Ellipse2D.Double(xP - 0, yP - 0, 1, 1));
        }
      }

      Calendar now = Calendar.getInstance();
      double seconds = (double) now.get(Calendar.SECOND) + (double) now.get(Calendar.MILLISECOND) / 1000;
      double minutes = (double) now.get(Calendar.MINUTE) + seconds / 60;
      double hours = (double) now.get(Calendar.HOUR) + minutes / 60;
      hours = hours > 12 ? hours - 12 : hours;

      double angleS = -90L + seconds * 6; // 360 / 60 = 6
      double angleSInRadians = Math.toRadians(angleS);
//      g.drawLine((int) xCenter, (int) yCenter, (int) (xCenter + ( (clockSize * 14) / 30) * Math.cos(angleSInRadians)), (int) (yCenter + ((clockSize * 14) / 30)  * Math.sin(angleSInRadians)));

      double angleM = -90L + minutes * 6; // 360 / 60 = 6
      double angleMInRadians = Math.toRadians(angleM);
      g.drawLine((int) xCenter, (int) yCenter, (int) (xCenter + ( (clockSize * 12) / 30) * Math.cos(angleMInRadians)), (int) (yCenter + ((clockSize * 12) / 30)  * Math.sin(angleMInRadians)));

      double angleH = -90L + hours * 30; // 360 / 12 = 30
      double angleHInRadians = Math.toRadians(angleH);
      g.drawLine((int) xCenter, (int) yCenter, (int) (xCenter + ( (clockSize * 10) / 30) * Math.cos(angleHInRadians)), (int) (yCenter + ((clockSize * 10) / 30)  * Math.sin(angleHInRadians)));

      // With shapes
      g2.setStroke(new BasicStroke(1));

      AffineTransform transform;

      Shape secondsHand = new Ellipse2D.Double(0, 0, (clockSize / 2) + 18, 4);
      transform = g2.getTransform();
      g2.translate(xCenter - 20, yCenter - 2);
      g2.rotate(angleSInRadians, 20, 2);
      g2.setPaint(Color.WHITE);
      g2.fill(secondsHand);
//      g2.setColor(Color.RED);
//      g2.draw(secondsHand);
      g2.setTransform(transform);

      g2.setColor(Color.BLACK);
      g2.fill(new Ellipse2D.Double(xCenter - 1, yCenter - 1, 3, 3));
    }
  }

  private static Dimension DIMENSION = new Dimension(100, 100);

  public MyTest() throws HeadlessException {
    super();
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    add(new ClockPanel());

    setRealSize((int) DIMENSION.getWidth(), (int) DIMENSION.getHeight());
  }

  private void setRealSize(int reqWidth, int reqHeight) {
    setVisible(true);

    // first set the size
    setSize(reqWidth, reqHeight);

// This is not the actual-sized frame. get the actual size
    Dimension actualSize = getContentPane().getSize();

    int extraW = reqWidth - actualSize.width;
    int extraH = reqHeight - actualSize.height;

// Now set the size.
    setSize(reqWidth + extraW, reqHeight + extraH);
  }

  public static void main(String[] args) {
    new MyTest();
  }

}
