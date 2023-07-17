package todoapp.core.todo.domain;

/**
 * 할일 상태 값
 *
 * @author springrunner.kr@gmail.com
 */
public enum TodoState {

  ACTIVE("active", "처리해야 할일"), 
  COMPLETED("completed", "완료된 할일");

  private final String literal;
  private final String description;

  TodoState(String literal, String description) {
    this.literal = literal;
    this.description = description;
  }

  public String getLiteral() {
    return literal;
  }

  public String getDescription() {
    return description;
  }

}
