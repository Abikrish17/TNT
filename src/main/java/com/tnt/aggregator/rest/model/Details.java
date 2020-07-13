package com.tnt.aggregator.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Objects;

/**
 * Details
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2020-07-07T16:29:38.570+02:00")

public class Details  implements Serializable {
  @JsonProperty("pricing")
  private Pricing pricing = null;

  @JsonProperty("track")
  private Track track = null;

  @JsonProperty("shipments")
  private Shipments shipments = null;

  public Details pricing(Pricing pricing) {
    this.pricing = pricing;
    return this;
  }

   /**
   * Get pricing
   * @return pricing
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Pricing getPricing() {
    return pricing;
  }

  public void setPricing(Pricing pricing) {
    this.pricing = pricing;
  }

  public Details track(Track track) {
    this.track = track;
    return this;
  }

   /**
   * Get track
   * @return track
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Track getTrack() {
    return track;
  }

  public void setTrack(Track track) {
    this.track = track;
  }

  public Details shipments(Shipments shipments) {
    this.shipments = shipments;
    return this;
  }

   /**
   * Get shipments
   * @return shipments
  **/
  @ApiModelProperty(value = "")

  @Valid

  public Shipments getShipments() {
    return shipments;
  }

  public void setShipments(Shipments shipments) {
    this.shipments = shipments;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Details details = (Details) o;
    return Objects.equals(this.pricing, details.pricing) &&
        Objects.equals(this.track, details.track) &&
        Objects.equals(this.shipments, details.shipments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(pricing, track, shipments);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Details {\n");

    sb.append("    pricing: ").append(toIndentedString(pricing)).append("\n");
    sb.append("    track: ").append(toIndentedString(track)).append("\n");
    sb.append("    shipments: ").append(toIndentedString(shipments)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

