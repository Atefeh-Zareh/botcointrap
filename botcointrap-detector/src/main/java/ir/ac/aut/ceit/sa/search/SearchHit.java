/**
 *  Copyright (C) 2011  Neil Taylor (https://github.com/qwerky/)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ir.ac.aut.ceit.sa.search;

public class SearchHit {
  
  private int line;
  private String highlight;

  public SearchHit(String highlight, int line) {
    this.highlight = highlight;
    this.line = line;
  }

  public String getHighlight() {
    return highlight;
  }

  public void setHighlight(String highlight) {
    this.highlight = highlight;
  }

  public int getLine() {
    return line;
  }

  public void setLine(int line) {
    this.line = line;
  }
  
  
  
}
