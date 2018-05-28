package org.bitbucket.r3bus.utils;

import java.time.LocalDate;
import java.util.Iterator;

public class LocalDateRange implements Iterable<LocalDate> {

	private Iterator<LocalDate> iterator;

	public static Iterable<LocalDate> with(LocalDate inizio, LocalDate fine) {
		return new LocalDateRange(inizio, fine);
	}

	private LocalDateRange(LocalDate inizio, LocalDate fine) {
		iterator = new LocalDateRangeIterator(inizio, fine);
	}

	private class LocalDateRangeIterator implements Iterator<LocalDate> {

		private LocalDate inizio;
		private LocalDate fine;

		public LocalDateRangeIterator(LocalDate inizio, LocalDate fine) {
			this.inizio = inizio;
			this.fine = fine;
		}

		@Override
		public boolean hasNext() {
			return inizio.compareTo(fine) <= 0;
		}

		@Override
		public LocalDate next() {
			LocalDate temp = inizio;
			inizio = inizio.plusDays(1);
			return temp;
		}

	}

	@Override
	public Iterator<LocalDate> iterator() {
		return iterator;
	}

}
