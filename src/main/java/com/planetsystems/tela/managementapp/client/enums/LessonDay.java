package com.planetsystems.tela.managementapp.client.enums;

public enum LessonDay {
	 MON("Monday") , TUE("Tuesday") , WED("Wednesday") , THURS("Thursday") ,
	    FRI("Friday") , SAT("Saturday") , SUN("Sunday");

	    private String day;

	    LessonDay(String day) {
	        this.day = day;
	    }

	    public String getDay() {
	        return day;
	    }

	    public void setDay(String day) {
	        this.day = day;
	    }

	    public static LessonDay getLessonDay(String day){
	        for (LessonDay lessonDay : LessonDay.values()) {
	            if (lessonDay.getDay().equalsIgnoreCase(day)) {
	                return lessonDay;
	            }
	        }
	        return null;
	    }

	    public static String getLessonDayValue(String day){
	        for (LessonDay lessonDay : LessonDay.values()) {
	            if (lessonDay.getDay().equalsIgnoreCase(day)) {
	                return lessonDay.getDay();
	            }
	        }
	        return null;
	    }


}
