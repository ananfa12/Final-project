﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated from a template.
//
//     Manual changes to this file may cause unexpected behavior in your application.
//     Manual changes to this file will be overwritten if the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace UpdateData
{
    using System;
    using System.Data.Entity;
    using System.Data.Entity.Infrastructure;
    using System.Data.Entity.Core.Objects;
    using System.Linq;
    
    public partial class DomainsEntities : DbContext
    {
        public DomainsEntities()
            : base("name=DomainsEntities")
        {
        }
    
        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            throw new UnintentionalCodeFirstException();
        }
    
        public virtual DbSet<tbGooglePR> tbGooglePRs { get; set; }
        public virtual DbSet<tbSource> tbSources { get; set; }
        public virtual DbSet<tbAlexa> tbAlexas { get; set; }
        public virtual DbSet<tbSplit> tbSplits { get; set; }
        public virtual DbSet<tbSrc> tbSrcs { get; set; }
        public virtual DbSet<tbStatu> tbStatus { get; set; }
        public virtual DbSet<tbDomainsFromSrc> tbDomainsFromSrcs { get; set; }
        public virtual DbSet<tbNewDomain> tbNewDomains { get; set; }
    
        public virtual int CreateDomainsTable()
        {
            return ((IObjectContextAdapter)this).ObjectContext.ExecuteFunction("CreateDomainsTable");
        }
    
        public virtual ObjectResult<Nullable<int>> CreateDomainsTableWithCnt()
        {
            return ((IObjectContextAdapter)this).ObjectContext.ExecuteFunction<Nullable<int>>("CreateDomainsTableWithCnt");
        }
    }
}
