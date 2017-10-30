package com.stu.javabean;

import android.os.Parcel;
import android.os.Parcelable;

public class Goods implements Parcelable {
	private String good_name;

	private double price;

	private String describle;

	private int count;

	private String imageName;

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getImgName() {
		return imageName;
	}

	public String getGood_name() {
		return good_name;
	}

	public void setGood_name(String good_name) {
		this.good_name = good_name;
	}

	public double getPrice() {
		return price;
	}

	public void setImgName(String imageName) {
		this.imageName = imageName;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDescrible() {
		return describle;
	}

	public void setDescrible(String describle) {
		this.describle = describle;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.good_name);
		dest.writeDouble(this.price);
		dest.writeString(this.describle);
		dest.writeInt(this.count);
		dest.writeString(this.imageName);
	}

	public Goods() {
	}

	protected Goods(Parcel in) {
		this.good_name = in.readString();
		this.price = in.readDouble();
		this.describle = in.readString();
		this.count = in.readInt();
		this.imageName = in.readString();
	}

	public static final Parcelable.Creator<Goods> CREATOR = new Parcelable.Creator<Goods>() {
		@Override
		public Goods createFromParcel(Parcel source) {
			return new Goods(source);
		}

		@Override
		public Goods[] newArray(int size) {
			return new Goods[size];
		}
	};

	@Override
	public String toString() {
		return "Goods [good_name=" + good_name + ", price=" + price
				+ ", describle=" + describle + ", count=" + count
				+ ", imageName=" + imageName + "]";
	}

	
}