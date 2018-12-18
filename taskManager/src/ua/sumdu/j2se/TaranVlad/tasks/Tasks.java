package ua.sumdu.j2se.TaranVlad.tasks;

import java.util.*;

/**
 * Class with 2 support method incoming and calendar
 *
 * @author Vlad Taran
 * @version 1.0 1 Dec 2018
 */

public class Tasks {

    /**
     * @return array with task that start from and end to
     *
     * @param tasks - array tasks
     * @param start starting time
     * @param end final time
     */
    public static Iterable<Task> incoming(Iterable<Task> tasks,
                                          Date start, Date end) {
        List<Task> aOutput = new ArrayList<>();
        for (Task temp : tasks) {
            if (temp.nextTimeAfter(start) != null) {
                if ((temp.nextTimeAfter(start).after(start)
                        && temp.nextTimeAfter(start).before(end))
                        || temp.nextTimeAfter(start).equals(end)) {
                    aOutput.add(temp);
                }
            }
        }
        return aOutput;
    }


    /**
     * @return calendar of tasks for a given period
     *
     * @param tasks - array tasks
     * @param start starting time
     * @param end final time
     */
    public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks,
                                                      Date start, Date end) {
        TreeMap<Date, Set<Task>> map = new TreeMap<>();
        HashSet<Date> dateHashSet = new HashSet<>();
        Date date;
        for (Task temp : tasks) {
            if (temp != null && temp.nextTimeAfter(start) != null) {
                date = start;
                do {
                    if ((temp.nextTimeAfter(date).after(start)
                            && temp.nextTimeAfter(date).before(end))
                            || temp.nextTimeAfter(date).equals(end)) {
                        map.put(temp.nextTimeAfter(date), new HashSet<Task>());
                        dateHashSet.add(temp.nextTimeAfter(date));
                        date = temp.nextTimeAfter(date);
                    } else date = temp.nextTimeAfter(date);
                } while (temp.nextTimeAfter(date) != null);
            }
        }
        for (Date dateMap : dateHashSet) {
            Set<Task> set = map.get(dateMap);
            for (Task temp : tasks) {
                if (temp != null && temp.nextTimeAfter(start) != null) {
                    date = start;
                    do {
                        if (temp.nextTimeAfter(date).equals(dateMap)) {
                            set.add(temp);
                            date = temp.nextTimeAfter(date);
                        } else date = temp.nextTimeAfter(date);
                    } while (temp.nextTimeAfter(date) != null);
                }
            }
            map.put(dateMap, set);
        }
        return map;
    }
}
