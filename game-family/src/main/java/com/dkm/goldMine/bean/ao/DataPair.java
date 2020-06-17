package com.dkm.goldMine.bean.ao;

import lombok.Data;

@Data
public class DataPair {
        public DataPair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        int x;
        int y;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            DataPair dataPair = (DataPair) o;

            if (x != dataPair.x) return false;
            return y == dataPair.y;
        }

        @Override
        public int hashCode() {
            int result = (int) (x ^ (x >>> 32));
            result = 31 * result + (int) (y ^ (y >>> 32));
            return result;
        }
    }
